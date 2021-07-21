package com.example.demo.vo;

public class BbsMemVO {
	private int num;
	private String id;
	private String pw;

	public BbsMemVO(){}
	public BbsMemVO(int num,String id,String pw){
		setNum(num);
		setId(id);
		setPw(pw);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "BbsMemVO [id=" + id + ", pw=" + pw + "]";
	}
}
