package com.chaofan.test;

import utils.NumGeneration;

public class Cart {
	private int number;
	private String id;
	private long startTime;
	private long endTime;
	
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Cart(int num) {
	}
	
	public Cart() {
		this.number = NumGeneration.generateNumber(1, 200);
//		this.number = 10;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
