package umk.zychu.inzynierka.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import umk.zychu.inzynierka.foo.Stock;
import umk.zychu.inzynierka.service.UserService;

@Controller
public class Websocket2Controller {
	

	  @Autowired 
	  private SimpMessagingTemplate template;  
	  @Autowired
	  private UserService service;
	  private TaskScheduler scheduler = new ConcurrentTaskScheduler();
	  private List<Stock> stockPrices = new ArrayList<Stock>();
	  private Random rand = new Random(System.currentTimeMillis());

	  
	  /**
	   * Iterates stock list, update the price by randomly choosing a positive
	   * or negative percentage, then broadcast it to all subscribing clients
	   */
	  private void updatePriceAndBroadcast() {
	    for(Stock stock : stockPrices) {
	      double chgPct = rand.nextDouble() * 5.0;
	      if(rand.nextInt(2) == 1) chgPct = -chgPct;
	      stock.setPrice(stock.getPrice() + (chgPct / 100.0 * stock.getPrice()));
	      stock.setTime(new Date());
	    }
	    //TOPIC SESSION
	    //template.convertAndSend("/topic/price", stockPrices);
	  }
	  
	  /**
	   * Invoked after bean creation is complete, this method will schedule 
	   * updatePriceAndBroacast every 1 second
	   */
	  @PostConstruct
	  private void broadcastTimePeriodically() {
	    scheduler.scheduleAtFixedRate(new Runnable() {
	      @Override public void run() {
	        updatePriceAndBroadcast();
	      }
	    }, 15000);
	  }
	  
	  /**
	   * Handler to add one stock
	   */
	  @MessageMapping("/addStock")
	  public void addStock(Stock stock) throws Exception {
	    stockPrices.add(stock);
	    updatePriceAndBroadcast();
	  }
	  
	  /**
	   * Handler to remove all stocks
	   */
	  @MessageMapping("/removeAllStocks")
	  public void removeAllStocks() {
	    stockPrices.clear();
	    updatePriceAndBroadcast();
	  }
	  
	  /**
	   * Serve the main page
	   */
	  @RequestMapping(value = "/stock", method = RequestMethod.GET)
	  public String home() {
	    return "stock";
	  }
}
