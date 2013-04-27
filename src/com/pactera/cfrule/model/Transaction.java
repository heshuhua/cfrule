package com.pactera.cfrule.model;

import java.util.Date;


public class Transaction {
	
	private String transactionId;
	
	private Date timestamp;
	
	private String origin;
	
	private String destination;
	
	private String transAddress;
	
	private long amount;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTransAddress() {
		return transAddress;
	}

	public void setTransAddress(String transAddress) {
		this.transAddress = transAddress;
	}
	
	
	
	

}
