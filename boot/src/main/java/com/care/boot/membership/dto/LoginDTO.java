package com.care.boot.membership.dto;
/*
CREATE TABLE login(
id varchar2(20),
pw varchar2(128),
CONSTRAINT pk_login PRIMARY KEY (id)
);

INSERT INTO login VALUES('admin', '1234');

SQL> commit;
Commit complete.
 */
public class LoginDTO {
	private String id;
	private String pw;
	private String confirmPw;
	
	public String getConfirmPw() {
		return confirmPw;
	}
	public void setConfirmPw(String confirmPw) {
		this.confirmPw = confirmPw;
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
	
}
