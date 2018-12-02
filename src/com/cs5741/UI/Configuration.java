package com.cs5741.UI;

public class Configuration {
	private int initTills;
	private int maxiumOfTill;
	private int rateOfGnrtTill;
	private int sizeOfEachTill;
	private int restrictiveTills;
	private int restrictiveNum;

	public Configuration(int initTills, int maxiumOfTill, int rateOfGnrtTill, int sizeOfEachTill, int restrictiveTills,
			int restrictiveNum) {
		super();
		this.initTills = initTills;
		this.maxiumOfTill = maxiumOfTill;
		this.rateOfGnrtTill = rateOfGnrtTill;
		this.sizeOfEachTill = sizeOfEachTill;
		this.restrictiveTills = restrictiveTills;
		this.restrictiveNum = restrictiveNum;
	}

	public Configuration() {
		this.initTills = 3;
		this.maxiumOfTill = 4;
		this.rateOfGnrtTill = 12;
		this.sizeOfEachTill = 5;
		this.restrictiveTills = 2;
		this.restrictiveNum = 100;
	}

	public int getSizeOfEachTill() {
		return sizeOfEachTill;
	}

	public void setSizeOfEachTill(int sizeOfEachTill) {
		this.sizeOfEachTill = sizeOfEachTill;
	}

	public int getRestrictiveNum() {
		return restrictiveNum;
	}

	public void setRestrictiveNum(int restrictiveNum) {
		this.restrictiveNum = restrictiveNum;
	}

	public int getInitTills() {
		return initTills;
	}

	public void setInitTills(int initTills) {
		this.initTills = initTills;
	}

	public int getMaxiumOfTill() {
		return maxiumOfTill;
	}

	public void setMaxiumOfTill(int maxiumOfTill) {
		this.maxiumOfTill = maxiumOfTill;
	}

	public int getRestrictiveTills() {
		return restrictiveTills;
	}

	public void setRestrictiveTills(int restrictiveTills) {
		this.restrictiveTills = restrictiveTills;
	}

	public int getRateOfGnrtTill() {
		return rateOfGnrtTill;
	}

	public void setRateOfGnrtTill(int rateOfGnrtTill) {
		this.rateOfGnrtTill = rateOfGnrtTill;
	}

	@Override
	public String toString() {
		return "Configuration [initTills=" + initTills + ", maxiumOfTill=" + maxiumOfTill + ", restrictiveTills="
				+ restrictiveTills + ", rateOfGnrtTill=" + rateOfGnrtTill + "]";
	}

}
