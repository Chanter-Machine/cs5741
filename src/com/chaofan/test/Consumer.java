package com.chaofan.test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Consumer implements /* Runnable, */ Callable<Long> {
	Container container;
	int id, size;
	volatile boolean switch_on;
	TillStatus tillStatus;
	long eachWaitTime;
	long TotalWaitTIme;
//	Role role;

	public Consumer(Container ct, int id, TillStatus tillStatus, Role role) {
		container = ct;
		this.id = id;
		switch_on = true;
		this.tillStatus = tillStatus;
//		this.role = role;
		TotalWaitTIme = 0;
	}

	public Consumer(Container ct, int id, TillStatus tillStatus, boolean switch_on) {
		container = ct;
		this.id = id;
		this.switch_on = switch_on;
		this.tillStatus = tillStatus;
		TotalWaitTIme = 0;
	}

//	public Role getRole() {
//		return role;
//	}
//	public void setRole(Role role) {
//		this.role = role;
//	}	

	public boolean isSwitch_on() {
		return switch_on;
	}

	public long getTotalWaitTIme() {
		return TotalWaitTIme;
	}

	public void setTotalWaitTIme(long totalWaitTIme) {
		TotalWaitTIme = totalWaitTIme;
	}

	public void setSwitch_on(boolean switch_on) {
		this.switch_on = switch_on;
	}

	// @Override
	public void run() {
//		System.out.println("Consumer #"+id+" is running");
		int num;
//		for(int i=0;i<100;i++) {
//		
//			Cart cart = container.get();
//
//			System.out.println("Consumer#"+this.id+" get "+ cart.getNumber());
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}

		while (switch_on) {
			Cart cart = container.get();
//			container.get();

			System.out.println("Consumer#" + this.id + " get " + cart.getNumber());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Long call() throws Exception {
		long startTime = System.currentTimeMillis();
		while (switch_on) {
			Cart cart = container.get();
			TotalWaitTIme += cart.getEndTime() - cart.getStartTime();
//			totalProducts += cart.getNumber()
//			container.get();

//			System.out.println("Consumer#"+this.id+" get "+ cart.getNumber());
//			try {
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
		}
		long endTime = System.currentTimeMillis();
		return startTime - endTime;
	}
}
