package com.ez.herb.board.model;

import java.sql.Timestamp;

//VO(Value Object), DTO(Data Transfer Object)
public class BoardVO {
	private int no;
	private String name;
	private String pwd;
	private String title;
	private String email;
	private Timestamp regdate;
	private int readcount;
	private String content;
	
	//24시간 이내의 글인 경우 image표시하기 위해 사용
	private double newImgTerm;
	
	
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public double getNewImgTerm() {
		return newImgTerm;
	}
	public void setNewImgTerm(double newImgTerm) {
		this.newImgTerm = newImgTerm;
	}
	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", name=" + name + ", pwd=" + pwd + ", title=" + title + ", email=" + email
				+ ", regdate=" + regdate + ", readcount=" + readcount + ", content=" + content + ", newImgTerm="
				+ newImgTerm + "]";
	}
	
	
	
	
	
	
	
	
}
