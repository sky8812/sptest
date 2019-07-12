package com.ez.herb.test;

public class ResultVO {
	private String message;
	private MemoVO data;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MemoVO getData() {
		return data;
	}
	public void setData(MemoVO data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResultVO [message=" + message + ", data=" + data + "]";
	}
	
	
}
