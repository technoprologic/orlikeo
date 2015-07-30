package umk.zychu.inzynierka.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import umk.zychu.inzynierka.controller.DTObeans.Greeting;
import umk.zychu.inzynierka.controller.DTObeans.HelloMessage;

@Component
@Controller
public class WebsocketController {
	
	@Autowired
	AmqpTemplate amqpTemplate;

	@RequestMapping("/websocket")
	public String websocket() {
		return "websocket";
	}

	@MessageMapping("/hello")
	@SendToUser("/queue/myqueue")
	public Greeting websocketInfo(HelloMessage message) throws Exception {
		/*System.out.println(("Hello, " + message.getName() + " " 	
				+ " !").toString());*/
		return new Greeting((String)amqpTemplate.receiveAndConvert("myqueue") + message.getName() + " oko");
	}

	@RequestMapping("/websocket2")
	public String websocket2() {
		return "websocket2";
	}

}

@Service
class RabbitService {

	@Autowired
	AmqpTemplate amqpTemplate;

	@Autowired
	SimpMessageSendingOperations template;

	@Scheduled(fixedDelay = 3000)
	public void sendAMQP() {

		/*String queue = "/queue/myqueue";
		System.out.println("scheduled");
		template.convertAndSendToUser("maria@o2.pl", queue, "dupa");*/

		//System.out.println("convert and send");
		amqpTemplate.convertAndSend("myqueue", "foo");
		
		/*
		 * String foo = (String) amqpTemplate.receiveAndConvert("myqueue");
		 * System.out.print("FOO" + foo);
		 */

	}
}
