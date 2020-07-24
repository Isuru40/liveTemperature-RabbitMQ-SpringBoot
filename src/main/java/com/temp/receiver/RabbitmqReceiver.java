package com.temp.receiver;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Service;

@Service
public class RabbitmqReceiver {
	
	
	@Autowired
	SimpMessagingTemplate template;

	  @RabbitListener(queues = "${livetemperature.rabbitmq.queue}")
	  public void receiveMessage(String message) {

		if(isNumeric(message)) {
			 System.out.println("Received " + message + "");
			template.convertAndSend("/topic/temperature", message);
		}

	  }

	  public  boolean isNumeric(String str)  
		{  
		  try  
		  {  
		    @SuppressWarnings("unused")
			double d = Double.parseDouble(str); 
		    System.out.println(d);
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}


}
