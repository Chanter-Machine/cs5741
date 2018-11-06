package com.chaofan.test;

import utils.NumGeneration;

public class Cart {
	private int number;
	private String id;
	
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
