package com.temp.sheduler;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Service
@EnableScheduling
public class Schedular {
	
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	

	@Value("${livetemperature.rabbitmq.exchange}")
	private String exchange;
		
	@Value("${livetemperature.rabbitmq.routingkey}")
	private String routingkey;	
	
	@Scheduled(cron ="0/5 * * * * ?")
	void sendmessage() {
		
		System.out.println("Running....");
	  //  System.out.println("Sending message...");
		int randomNum = ThreadLocalRandom.current().nextInt(0, 35 + 1);
		amqpTemplate.convertAndSend(exchange, routingkey, randomNum);
	
		
	}

}
