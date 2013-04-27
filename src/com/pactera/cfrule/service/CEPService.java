package com.pactera.cfrule.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




public class CEPService {
	
	
	AmqpTemplate amqpTemplate;
	
	CachingConnectionFactory connectionFactory;
	
	SimpleMessageListenerContainer container;
	
	public CEPService(CachingConnectionFactory connectionFactory,AmqpTemplate amqpTemplate,SimpleMessageListenerContainer container) {
		System.out.println("cep service loaded");
		//container.setConnectionFactory(connectionFactory);
		container.setQueueNames("monqueue");
		
		//amqpTemplate.convertAndSend("normalexchange","normalqueue", "0:0:0:0");
		
		container.setMessageListener(new MessageListenerAdapter(new CEPHandler(amqpTemplate)));
		System.out.println("init listener loaded");
		
		
		
	}
	

	
}
