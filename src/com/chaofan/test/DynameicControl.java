package com.chaofan.test;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.chaofan.UI.Configuration;

public class DynameicControl implements Runnable {

	Vector<Container> containers;
	Configuration configuration;
	ExecutorService service;
	int numOfCartInRestrativeTills;
	int numOfCartInCommonTills;
	Vector<Future<Long>> timeConsumationOfConsumer;
	Vector<Consumer> consumers;
	
	public DynameicControl(Vector<Container> containers, Configuration configuration,
			ExecutorService service, Vector<Future<Long>> timeConsumationOfConsumer,
			Vector<Consumer> consumers) {
		this.containers = containers;
		this.configuration = configuration;
		this.service = service;
		this.timeConsumationOfConsumer = timeConsumationOfConsumer;
		this.consumers = consumers;
	}
	
	public int getAverage(int tills, int totalCarts) {
		return totalCarts/tills;
	}
	
	public void checkNum() {
		numOfCartInRestrativeTills = 0;
		numOfCartInCommonTills = 0;
		int averageRestrative = 0;
		int averageCommon = 0;
		for(int i=0;i<configuration.getRestrictiveTills();i++) {
			numOfCartInRestrativeTills += containers.get(i).getList().size();
		}
		averageRestrative = getAverage(configuration.getRestrictiveTills(), numOfCartInRestrativeTills);
		if(averageRestrative >= configuration.getSizeOfEachTill()-1) {
			if(containers.size()<configuration.getMaxiumOfTill()) {
//				openTill(configuration.getRestrictiveTills());
//				openTill(configuration.getInitTills());
				configuration.setInitTills(configuration.getInitTills()+1);
				configuration.setRestrictiveTills(configuration.getRestrictiveTills()+1);
				
			}
		}
		else if(configuration.getRestrictiveTills()>1 && averageRestrative< configuration.getSizeOfEachTill()/2) {
//			System.out.println("Till "+findMinIndex(0, configuration.getRestrictiveTills())+" is closing");
//			closeTill(findMinIndex(0, configuration.getRestrictiveTills()), "restrctive");
		}
		
		for(int i=configuration.getRestrictiveTills();i<configuration.getInitTills();i++) {
			numOfCartInCommonTills += containers.get(i).getList().size();
		}
		averageCommon = getAverage(configuration.getInitTills()-configuration.getRestrictiveTills(), numOfCartInCommonTills);
		if(averageCommon >= configuration.getSizeOfEachTill()-1) {
			if(containers.size()<configuration.getMaxiumOfTill()) {
//				openTill(configuration.getInitTills());
				configuration.setInitTills(configuration.getInitTills()+1);
			}
		}
		else if(configuration.getInitTills()-configuration.getRestrictiveTills()>=2 && averageCommon< configuration.getSizeOfEachTill()/2) {
			
		}
	}
	
//	public void openTill(int index) {
//		System.out.println("Till is opening"+index);
//		containers.add(index, new ContainerTest3(configuration.getSizeOfEachTill()));
//		Consumer consumer = new Consumer(containers.get(index), index);
//		consumers.add(index,consumer);
//		timeConsumationOfConsumer.add(service.submit(consumer));
//	}
//	
//	public void closeTill(int index, String type) {
//		System.out.println("Till "+index+" closing");
//		containers.get(index).setTillStatus(TillStatus.closing);
//		if(containers.get(index).getList().size()==0) {
//			if(type.equals("restrctive")) {
//				configuration.setRestrictiveNum(configuration.getRestrictiveTills()-1);
//			}
//			configuration.setInitTills(configuration.getInitTills()-1);
//			configuration.setRestrictiveNum(configuration.getInitTills()-1);
//			consumers.get(index).setSwitch_on(false);
//			containers.remove(index);
//			consumers.remove(index);
//		}
//	}
	
	public int findMinIndex(int start, int end) {
		int min = 200;
		int index = 0;
		for(int i=start;i<end;i++) {
			if(min>containers.get(i).getList().size()) {
				min = containers.get(i).getList().size();
				index = i;
			}
		}
		return index;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				checkNum();
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
