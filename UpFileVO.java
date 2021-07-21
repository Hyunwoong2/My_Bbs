package com.example.demo.vo;

public class UpFileVO {
	private int fileNum;
	private int num;
	private String filename;
	private long filesize;
	
	public UpFileVO() {}
	
	public UpFileVO(int fileNum,int num, String filename, long filesize) {
		this.fileNum = fileNum;
		this.num = num;
		this.filename = filename;
		this.filesize = filesize;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
}
