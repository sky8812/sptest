package com.ez.herb.cart.model;

import java.sql.Timestamp;

public class CartVO {
	private int cartNo;
	private String customerId;
	private int productNo;
	private int quantity;
	private Timestamp regdate;
	
	public int getCartNo() {
		return cartNo;
	}
	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "CartVO [cartNo=" + cartNo + ", customerId=" + customerId + ", productNo=" + productNo + ", quantity="
				+ quantity + ", regdate=" + regdate + "]";
	}
	
	
}
