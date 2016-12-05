package umk.zychu.inzynierka.executors;

/**
 * Created by emag on 20.08.16.
 */
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.model.Graphic;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

@Component
public class OrliksTaskExecutor extends BaseTaskExecutor{

    private TaskExecutor taskExecutor;
    private static final long FIXED_DELAY = 1000;

    public OrliksTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Scheduled(fixedDelay = FIXED_DELAY)
    public void printMessages() {
        Task task = new Task();
        try{
            taskExecutor.execute(task);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Class that contains runnable task.
     */
    private class Task implements Runnable {

        public Task() {
        }

        /**
         * Task to run by executor.
         */
        @Override
        @Transactional
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
            //removeAllBrokenEvents();
        }
    }

}
