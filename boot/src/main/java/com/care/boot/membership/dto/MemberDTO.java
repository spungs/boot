package com.care.boot.membership.dto;
/*
CREATE TABLE member(
id varchar2(20),
gender varchar2(2),
email varchar2(40),
CONSTRAINT pk_member FOREIGN KEY (id) REFERENCES login(id) ON DELETE CASCADE
);


SQL> commit;
Commit complete.
 
 */
public class MemberDTO extends LoginDTO{
	private String gender;
	private String email;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
