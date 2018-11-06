package com.chaofan.test;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.chaofan.UI.Configuration;

public class Producer implements /*Runnable*/ Callable<Integer>{
	Vector<Container> containers;
	int size, id;
	volatile boolean switch_on;
	private Configuration configuration;
	Integer sumOfCustomer, sumOfLostCustomer;
	
	public Producer(Vector<Container> cts, int id, Configuration configuration) {
		containers = cts;
		this.id = id;
		switch_on = true;
		this.configuration = configuration;
		sumOfCustomer = new Integer(0);
		sumOfLostCustomer = new Integer(0);
	}
	
	
	public boolean isSwitch_on() {
		return switch_on;
	}

	public void setSwitch_on(boolean switch_on) {
		this.switch_on = switch_on;
	}

	public void addToVector() {
		Cart cart = new Cart();
		sumOfCustomer ++;
		int index ;
		if(cart.getNumber()<=configuration.getRestrictiveNum()) {
//			System.out.println("end input"+(configuration.getRestrictiveTills()-1));
			index = countNumInTills(0, configuration.getRestrictiveTills()-1);
		}
		else {
			index = countNumInTills(configuration.getRestrictiveTills(), containers.size()-1);
		}
		if(index!=-1) {
//			
			containers.get(index).put(cart);
//			System.out.println("put "+cart.getNumber()+" to cart"+index );
		}
		else {
			sumOfLostCustomer++;
		}
	}
	
	/**
	 * Count num of carts in each Till,
	 * find till contains minumum tills.
	 */
	public int countNumInTills(int start, int end) {
		int numOfCarts = 200;
		int index=0;
		if(containers.get(end).getList().size()==configuration.getSizeOfEachTill()) {
			return -1;
		}
		for(int i=start;i<=end;i++) {
			if(containers.get(i).getTillStatus()==TillStatus.opening && containers.get(i).getList().size()<numOfCarts) {
				numOfCarts = containers.get(i).getList().size();
				index=i;
			}
			
//			System.out.println("size of container "+i+" is"+containers.get(i).getList().size());
		}
//		System.out.println("index="+index);
		return index;
	}
	

//	@Override
	public void run() {
		System.out.println("Producer# "+this.id+" is running now");
//		for(int i=0;i<100;i++) {
//			addToVector();
//			System.out.println("Producer#"+this.id+"put "+i);
//			try {
//				TimeUnit.SECONDS.sleep(configuration.getRateOfGnrtTill());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		while(switch_on) {
			System.out.println("Producer# "+this.id+" is running now");
			addToVector();
//			System.out.println("Producer#"+this.id+"put ");
			try {
//				TimeUnit.SECONDS.sleep(configuration.getRateOfGnrtTill());
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public Integer call() throws Exception {
		System.out.println("Producer is running");
		while(switch_on) {
//			System.out.println("Producer# "+this.id+" is running now");
			addToVector();
//			System.out.println("Producer#"+this.id+"put ");
			try {
//				TimeUnit.SECONDS.sleep(configuration.getRateOfGnrtTill());
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		return sumOfLostCustomer;
	}

	
}
