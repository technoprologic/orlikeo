package umk.zychu.inzynierka.executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.service.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

@Component
public class GraphicsTaskExecutor extends BaseExecutor{

    public GraphicsTaskExecutor() {
        super();
    }

    GraphicsTaskExecutor(TaskExecutor taskExecutor) {
        super();
        this.taskExecutor = taskExecutor;
    }

    /**
     * Scheduled task for execution.
     */
    @Scheduled(fixedRate = 10000)
    public void doTheJob() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                currentDate = new Date();
                NOW = currentDate.getTime();

                // Get all where is less than 30 minutes to start event.
                List<Graphic> halfHourToStartGraphics = graphicService.findAll().stream()
                        .filter(g -> g.getStartTime().getTime() - NOW < HALF_AN_HOUR).collect(Collectors.toList());

                // DIVIDING BY TIME :
                // true means it 0-15 min left,
                // false means it's 15-30 left.
                Map<Boolean, List<Graphic>> map = halfHourToStartGraphics.stream()
                        .collect(partitioningBy(g -> g.getStartTime().getTime() - NOW < QUARTER_OF_AN_HOUR));

                blockGraphics(halfHourToStartGraphics);
                manageByEventsState(map.get(Boolean.FALSE), event -> event.getState().equals(inBuild));
                manageByEventsState(map.get(Boolean.TRUE), event -> event.getState().equals(inBuild)
                        || event.getState().equals(toApproveState)
                        || event.getState().equals(threatenedState));

                clearEventsGraphicsEnded30MinutesAgo();
                clear45minutesPastGraphics();

                //TODO Find better solution for windows block problems than this function:
                // Removes events if status is other than IN_BASKET and graphic is NULL.
                removeAllBrokenEvents();
            }
        });
    }
}