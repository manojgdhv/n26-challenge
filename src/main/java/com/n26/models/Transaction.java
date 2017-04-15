package com.n26.models;

import java.io.Serializable;

public class Transaction implements Serializable{
	private static final long serialVersionUID = 3378176035436806586L;
	
	private double amount;
	private long timestamp;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
