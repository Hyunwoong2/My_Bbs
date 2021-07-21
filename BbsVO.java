package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

public class BbsVO {
	private int num;
	private String title;
	private String writer;
	private java.sql.Date wdate;
	private String contents;
	private int hit;
	private int pnum;
	private int lvl;
	private int topid;

	public BbsVO() {}
	public BbsVO(int num, String title, String writer, String contents, int hit, int pnum) {
		setNum(num);
		setTitle(title);
		setWriter(writer);
		setContents(contents);
		setHit(hit);
		setPnum(pnum);
	}
	@Override
	public String toString() {
		return String.format("%d %s %s %s %d %d<br>",num,title,writer,wdate,lvl,topid);
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public java.sql.Date getWdate() {
		return wdate;
	}
	public void setWdate(java.sql.Date wdate) {
		this.wdate = wdate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getPnum() {
		return pnum;
	}
	public void setPnum(int pnum) {
		this.pnum = pnum;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public List<UpFileVO> getFs() {
		return fs;
	}
	public void setFs(List<UpFileVO> fs) {
		this.fs = fs;
	}
	public int getTopid() {
		return topid;
	}
	public void setTopid(int topid) {
		this.topid = topid;
	}
}
