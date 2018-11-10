package com.chaofan.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContainerTest3 implements Container {

	private List<Cart> li;
	private List<Cart> list;
	private int size;
	Lock lock = new ReentrantLock();
	Condition notFull;
	Condition notEmpty;
	TillStatus tillStatus;
	Role role;
	public ContainerTest3(int size) {
		li = new ArrayList<Cart>(size);
		list = Collections.synchronizedList(li);
		this.size = size;
		notEmpty = lock.newCondition();
		notFull = lock.newCondition();
//		tillStatus = TillStatus.opening;
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public TillStatus getTillStatus() {
		return tillStatus;
	}

	public void setTillStatus(TillStatus tillStatus) {
		this.tillStatus = tillStatus;
	}

	public synchronized List<Cart> getList() {
		return list;
	}

	public void setList(List<Cart> list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Condition getNotFull() {
		return notFull;
	}

	public void setNotFull(Condition notFull) {
		this.notFull = notFull;
	}

	public Condition getNotEmpty() {
		return notEmpty;
	}

	public void setNotEmpty(Condition notEmpty) {
		this.notEmpty = notEmpty;
	}

	public synchronized void removeFromList(int index) {
		list.remove(index);
	}
	
	public void put(Cart cart) {

		boolean locked = false;
		try {
			locked = lock.tryLock(1, TimeUnit.SECONDS);

			if (locked) {
				while (list.size() >= size) {
					System.out.println("put is called wait");
					notFull.await();
				}
				list.add(cart);
				notEmpty.signalAll();
			} else {
				System.out.println("Producer did not get the lock");
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			if (locked) {
				lock.unlock();
			}
		}

	}

	public Cart get() {
		Cart cart = null;
		lock.lock();
		while (list.size() == 0) {
			try {
				System.out.println("get is called wait");
				notEmpty.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		System.out.println("consuemr get the lock");
		notFull.signalAll();
		cart = list.get(0);
		lock.unlock();

		if (cart != null) {
			int tmpNum = list.get(0).getNumber();
			while (tmpNum > 0) {
				list.get(0).setNumber(tmpNum--);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		lock.lock();
		list.remove(0);
		notFull.signalAll();
		lock.unlock();

		return cart;
	}

//	public Cart get() {
//		int num = 0;
//		lock.lock();
//		while (list.size() == 0) {
//			System.out.println("get is called wait");
//			try {
//				notEmpty.await();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		lock.unlock();
//
//		Cart cart = list.get(0);
//		while(cart.getNumber()>=0) {
//			try {
//				TimeUnit.MICROSECONDS.sleep(10);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//		lock.lock();
//		list.remove(0);
//		notFull.signalAll();
//		lock.unlock();
//
//		return cart;
//	}

}
