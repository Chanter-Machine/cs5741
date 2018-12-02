package com.cs5741.test;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.cs5741.UI.Configuration;

public class CustomerGenerator implements /* Runnable */ Callable<Integer> {
	Vector<Container> containers;
	int size, id;
	volatile boolean switch_on;
	private Configuration configuration;
	Integer sumOfCustomer, sumOfLostCustomer;
	int totalProducts;

	public CustomerGenerator(Vector<Container> cts, int id, Configuration configuration) {
		containers = cts;
		this.id = id;
		switch_on = true;
		this.configuration = configuration;
		sumOfCustomer = new Integer(0);
		sumOfLostCustomer = new Integer(0);
		totalProducts = 0;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public Integer getSumOfCustomer() {
		return sumOfCustomer;
	}

	public void setSumOfCustomer(Integer sumOfCustomer) {
		this.sumOfCustomer = sumOfCustomer;
	}

	public Integer getSumOfLostCustomer() {
		return sumOfLostCustomer;
	}

	public void setSumOfLostCustomer(Integer sumOfLostCustomer) {
		this.sumOfLostCustomer = sumOfLostCustomer;
	}

	public boolean isSwitch_on() {
		return switch_on;
	}

	public void setSwitch_on(boolean switch_on) {
		this.switch_on = switch_on;
	}

	public void addToVector() {
		Cart cart = new Cart();
		totalProducts += cart.getNumber();
		sumOfCustomer++;
		int index;
//		System.out.println("cart num: "+cart.getNumber());
//		System.out.println("configuration.getrestrictiveNum: " +configuration.getRestrictiveNum());
		if (cart.getNumber() <= configuration.getRestrictiveNum()) {
//			index = countNumInTills(0, configuration.getRestrictiveTills()-1);
			index = findTill2Put(Role.restrictive, cart.getNumber());
//			System.out.println("index = "+index);
		} else {
//			index = countNumInTills(configuration.getRestrictiveTills(), containers.size()-1);
			index = findTill2Put(Role.general, cart.getNumber());
		}
		if (index != -1) {
			containers.get(index).put(cart);
			cart.setStartTime(System.currentTimeMillis());
//			System.out.println("put "+cart.getNumber()+" to "+ index);
		} else {
			sumOfLostCustomer++;
		}
	}

	/**
	 * Count num of carts in each Till, find till contains minumum tills.
	 */
//	public int countNumInTills(int start, int end) {
//		int numOfCarts = 200;
//		int index=0;
//		if(containers.get(end).getList().size()==configuration.getSizeOfEachTill()) {
//			return -1;
//		}
//		for(int i=start;i<=end;i++) {
//			if(containers.get(i).getTillStatus()==TillStatus.opening && containers.get(i).getList().size()<numOfCarts) {
//				numOfCarts = containers.get(i).getList().size();
//				index=i;
//			}
//			
////			System.out.println("size of container "+i+" is"+containers.get(i).getList().size());
//		}
////		System.out.println("index="+index);
//		return index;
//	}

	public int findTill2Put(Role role, int num) {
		int index = 0;
		int min = 200;
		for (int i = 0; i < configuration.getMaxiumOfTill(); i++) {
//			System.out.println("container "+i+" statue: "+containers.get(i).getTillStatus()+" role: "+containers.get(i).getRole());
			if (containers.get(i).getTillStatus() == TillStatus.opening && role == containers.get(i).getRole()) {
				if (min > containers.get(i).getList().size()) {
//					System.out.println("num = "+num+"");
					index = i;
					min = containers.get(i).getList().size();
//					System.out.println("genereal "+ num);
				}
			}
//			else if(containers.get(i).getTillStatus()==TillStatus.opening  && role==containers.get(i).getRole()) {
//				if(min>containers.get(i).getList().size()) {
//					index=i;
//					min=containers.get(i).getList().size();
////					System.out.println("restrctive "+ num);
//				}
//			}
		}
		if (containers.get(index).getList().size() == configuration.getSizeOfEachTill()) {
			return -1;
		} else {
//			System.out.println("index :"+ index);
			return index;
		}
	}

//	@Override
	public void run() {
		System.out.println("Producer# " + this.id + " is running now");

		while (switch_on) {
			System.out.println("Producer# " + this.id + " is running now");
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
		while (switch_on) {
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

//	@Override
//	public Integer call() throws Exception {
//		System.out.println("Producer is running");
//		if(switch_on) {
////			System.out.println("Producer# "+this.id+" is running now");
//			addToVector();
////			System.out.println("Producer#"+this.id+"put ");
//			try {
////				TimeUnit.SECONDS.sleep(configuration.getRateOfGnrtTill());
//				TimeUnit.SECONDS.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	
//		return sumOfLostCustomer;
//	}
}
