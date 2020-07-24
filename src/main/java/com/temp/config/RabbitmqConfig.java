package com.temp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.temp.receiver.RabbitmqReceiver;
public class RabbitmqConfig {
		
	@Value("${livetemperature.rabbitmq.exchange}")
	private String exchange;
		
	@Value("${livetemperature.rabbitmq.queue}")
	private String queueName;
		
	@Value("${livetemperature.rabbitmq.routingkey}")
	private String routingkey;		

	  @Bean
	  Queue queue() {
	    return new Queue(queueName, false);
	  }

	  @Bean
	  TopicExchange exchange() {
	    return new TopicExchange(exchange);
	  }

	  @Bean
	  Binding binding(Queue queue, TopicExchange exchange) {
		  System.out.println("ok....");
	    return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	  }

	  @Bean
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(RabbitmqReceiver receiver) {
	    return new MessageListenerAdapter(receiver, "receiveMessage");
	  }

}
