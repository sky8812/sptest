package com.ez.herb.test;

public class MemoVO {
	private int no;
	private String name;
	private String content;
	
	
	public MemoVO() {
		super();
	}
	
	
	public MemoVO(int no, String name, String content) {
		super();
		this.no = no;
		this.name = name;
		this.content = content;
	}


	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MemoVO [no=" + no + ", name=" + name + ", content=" + content + "]";
	}
	
	
}
