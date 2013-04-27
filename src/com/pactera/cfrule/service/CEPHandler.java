package com.pactera.cfrule.service;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.pactera.cfrule.model.Transaction;


public class CEPHandler {

	
	static AmqpTemplate amqpTemplate;
	KnowledgeBuilder kbuilder;
	KnowledgeBaseConfiguration config;
	KnowledgeBase knowledgeBase;
	StatefulKnowledgeSession ksession;
	
	public CEPHandler(AmqpTemplate amqpTemplate2) {
		amqpTemplate = amqpTemplate2;
		initCEPRuntime();
	}
	
	public void initCEPRuntime()
	{
		kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	
		
		kbuilder.add(new ClassPathResource("ceprules.drl", getClass()),ResourceType.DRL);
		knowledgeBase = kbuilder.newKnowledgeBase();
		
		knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		
		  KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
	        //conf.setOption(ClockTypeOption.get("pseudo"));

	        ksession = knowledgeBase.newStatefulKnowledgeSession(conf, null);

	        new Thread() {
	            @Override
	            public void run() {
	                ksession.fireUntilHalt();
	            }
	        }.start();
	}

	public void handleMessage(String text) {
		System.out.println("Received: " + text);
		
		Transaction transaction = new Transaction();
		populateTrans(transaction,text);
		//transaction.setTransactionId(text);
		WorkingMemoryEntryPoint flightArrivalEntryPoint = ksession.getWorkingMemoryEntryPoint("transaction");
        flightArrivalEntryPoint.insert(transaction);
		
		
	}
	
	public static void sendtoWarn(String text)
	{
		amqpTemplate.convertAndSend("warnexchange","warnqueue", text);
	}

	private void populateTrans(Transaction transaction, String text) {
		
		String[] trans = text.split(":");
		transaction.setTransactionId(trans[0]);
		transaction.setOrigin(trans[1]);
		transaction.setAmount(new Long(trans[2]));
		transaction.setTransAddress(trans[3]);
		
	}
	
	
	

}
