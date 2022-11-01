package com.care.boot.board.dto;
/*
CREATE TABLE quiz_board(
no number,
id varchar2(20),
title varchar2(100),
content varchar2(2000),
filename varchar2(300),
writedate varchar2(15),
hit number,
primary KEY(no)
);
 */
public class BoardDTO {
	private int no;
	private String id;
	private String title;
	private String content;
	private String fileName;
	private String writeDate;
	private int hit;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	
}
