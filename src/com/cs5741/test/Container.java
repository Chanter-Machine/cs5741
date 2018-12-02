package com.cs5741.test;

import java.util.List;
import java.util.concurrent.locks.Condition;

public interface Container {
	public void put(Cart cart);
	public Cart get();
	public List<Cart> getList();
	public Condition getNotFull();
	public Condition getNotEmpty();
	public TillStatus getTillStatus();
	public void setTillStatus(TillStatus tillStatus);
	public Role getRole();
	public void setRole(Role role);
	public int getTotalProducts();
}
