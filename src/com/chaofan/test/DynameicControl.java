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
	
	public void closeTill(Role role) {
		if(role==Role.restrictive) {
			System.out.println("Till No. "+ findMinIndex(Role.restrictive)+" is closing");
			int index = findMinIndex(Role.restrictive);
			containers.get(index).setTillStatus(TillStatus.closing);
			while (containers.get(index).getList().size()>0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			consumers.get(index).setSwitch_on(false);
			configuration.setRestrictiveTills(configuration.getRestrictiveTills()-1);
		}
		else {
			System.out.println("Till No. "+ findMinIndex(Role.general)+" is closing");
			int index = findMinIndex(Role.general);
			containers.get(index).setTillStatus(TillStatus.closing);
			while (containers.get(index).getList().size()>0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			consumers.get(index).setSwitch_on(false);
//			configuration.setRestrictiveTills(configuration.getRestrictiveTills()-1);
		}
//		System.out.println("Till is opening"+index);
//		containers.add(index, new ContainerTest3(configuration.getSizeOfEachTill()));
//		Consumer consumer = new Consumer(containers.get(i), i, TillStatus.opening, Role.restrictive);
//		consumers.add(index,consumer);
//		timeConsumationOfConsumer.add(service.submit(consumer));
	}
	
	public int openTill(Role role) {
		System.out.println("A "+role+" need to be open");
		for(int i=0; i<configuration.getMaxiumOfTill();i++) {
			if(containers.get(i).getTillStatus()==TillStatus.closing 
					&& containers.get(i).getList().size() ==0
					&& consumers.get(i).isSwitch_on()==false) {
				consumers.get(i).setSwitch_on(true);
				containers.get(i).setRole(role);
				containers.get(i).setTillStatus(TillStatus.opening);
				if(role==Role.restrictive) {
					configuration.setRestrictiveTills(configuration.getRestrictiveTills()+1);
				}
				timeConsumationOfConsumer.add(service.submit(consumers.get(i)));
				return 0;
			}
		}
		return -1;
	}
	
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
	
//	public int findMinIndex(int start, int end) {
//		int min = 200;
//		int index = 0;
//		for(int i=start;i<end;i++) {
//			if(min>containers.get(i).getList().size()) {
//				min = containers.get(i).getList().size();
//				index = i;
//			}
//		}
//		return index;
//	}
	
	public int findMinIndex(Role role) {
		int index =0;
		int min = 100;
		for(int i=0;i<configuration.getMaxiumOfTill();i++) {
			if(role==containers.get(i).getRole() && containers.get(i).getTillStatus()==TillStatus.opening) {
				if(containers.get(i).getList().size()<min) {
					min = containers.get(i).getList().size();
					index = i;
				}
			}
		}
		return index;
	}
	
	
	
	/**
	 * Check num of cart in each till, if average num of restrictive tills 
	 * or general tills have too many carts waiting, a new till will open
	 * if there is an available one. If averange num is less than half, a till
	 * will be closed.
	 */
	public int checkNum2() {
		numOfCartInRestrativeTills = 0;
		numOfCartInCommonTills = 0;
		int averageRestrative = 0;
		int averageCommon = 0;
		int numOfRestrativeTIlls = 0;
		int numOfGeneralTills = 0;
		
		for(int i=0; i< configuration.getMaxiumOfTill(); i++) {
			if(containers.get(i).getTillStatus()==TillStatus.opening) {
				if(containers.get(i).getRole()==Role.restrictive) {
					numOfCartInRestrativeTills += containers.get(i).getList().size();
					numOfRestrativeTIlls++;
				}
				else {
					numOfCartInCommonTills += containers.get(i).getList().size();
					numOfGeneralTills++;
				}
			}
			
		}
		
		averageRestrative = getAverage(numOfRestrativeTIlls, numOfCartInRestrativeTills);
		averageCommon = getAverage(numOfGeneralTills, numOfCartInCommonTills);
		if(numOfRestrativeTIlls>1 && averageRestrative<= configuration.getSizeOfEachTill()/2) {
			System.out.println(numOfRestrativeTIlls);
			closeTill(Role.restrictive);
//			return 0;
		}
		else if(averageRestrative >= configuration.getSizeOfEachTill()-1) {
//			System.out.println(averageRestrative);
			openTill(Role.restrictive);
			return 0;
		}
		
		if(numOfGeneralTills>2 && averageCommon <= configuration.getSizeOfEachTill()/2) {
//			System.out.println(averageCommon+" "+ numOfGeneralTills);
			closeTill(Role.general);
		}
		else if(averageCommon >= configuration.getSizeOfEachTill()-1) {
			openTill(Role.general);
			return 0;
		}
		return 0;
	}
	
	
	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		while(true) {
			try {
				checkNum2();
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
