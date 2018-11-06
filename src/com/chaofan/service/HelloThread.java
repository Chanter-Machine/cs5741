package com.chaofan.service;

public class HelloThread extends Thread {

	HelloThread otherThread;

	int thNum;

	HelloThread(int inNum)

	{

		thNum = inNum;

	}

	void setOtherName(HelloThread inTh)

	{

		otherThread = inTh;

	}

	public void run() {

		System.out.println("Hello from a thread!");

		System.out.print("<<<< " + Thread.currentThread().getName() + " >  " + thNum + " >>>");

		if (thNum == 1)

		{

			for (int i = 1; i < 100; i++) {

				System.out.println("Count is: " + i);

			}

		}

		else

		{

			// while loop checking is the other thread alive??????

			while (otherThread.isAlive())

			{

				System.out.println("just a small check!"); // NOT GOOD use of the CPU

			}

			System.out.println("Other thread is NOW DEAD!!");

		}

	}

	public static void main(String args[]) {

		HelloThread temp1 = new HelloThread(1);

		HelloThread temp2 = new HelloThread(2);

		temp1.setOtherName(temp2);

		temp2.setOtherName(temp1);

		temp1.start();

		temp2.start();

		// temp.run(); this is bad bad bad

	}

}