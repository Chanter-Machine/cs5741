package com.chaofan.service;

public class JoinExample {

	static int tot1 = 0;

	static int tot2 = 0;

	static int grandTotal;

	public static void main(String args[]) {

		AutoRun myAuto1 = new AutoRun(1, 300);

		AutoRun myAuto2 = new AutoRun(301, 600);
//
//		AutoRun myAuto3 = new AutoRun(601, 800);

		System.out.println(" Thread name " + Thread.currentThread().getName());

		//

		for (int i = 301; i < 501; i++) {

			tot2 = tot2 + i;

		}

		System.out.println(" Total 2 " + tot2);

//		grandTotal = tot2 + myAuto1.getResult() + myAuto2.getResult() + myAuto3.getResult();
		grandTotal = tot2 + myAuto1.getResult() + myAuto2.getResult() ;
//		grandTotal = tot2 + myAuto1.getResult();

		System.out.println(" Total  " + grandTotal);

	}

}

// Automatic start on construction

class AutoRun implements Runnable {

	static int tot1 = 0;

	int num1;

	int num2;

	Thread tempT;

	public AutoRun(int inNum1, int inNum2) {

		num1 = inNum1;

		num2 = inNum2;

		tempT = new Thread(this);

		tempT.start();

	}

	public int getResult() {

		try {

			tempT.join();

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

		return tot1;

	}

	public void run() {

		System.out.println("AutoRun.run()");

		System.out.println(" Thread name " + Thread.currentThread().getName());

		for (int i = num1; i < num2 + 1; i++) {

			tot1 = tot1 + i;

		}

		System.out.println(" Total 1 " + tot1);

	}

}