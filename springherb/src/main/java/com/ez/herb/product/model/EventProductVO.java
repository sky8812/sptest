package com.ez.herb.product.model;

import com.ez.herb.common.SearchVO;

public class EventProductVO extends SearchVO{
	private int eventProductNo;
	private int productNo;
	private String eventName;
	
	public int getEventProductNo() {
		return eventProductNo;
	}
	public void setEventProductNo(int eventProductNo) {
		this.eventProductNo = eventProductNo;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	@Override
	public String toString() {
		return "EventProductVO [eventProductNo=" + eventProductNo + ", productNo=" + productNo + ", eventName="
				+ eventName + ", toString()=" + super.toString() + "]";
	}
	
	
}
