package com.care.boot.board.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.board.dto.BoardDTO;

public interface IBoardService {
	/*
	 ubuntu@ip-172-31-13-122:~$ cd /opt/tomcat/tomcat-9/webapps
	 ubuntu@ip-172-31-13-122:/opt/tomcat/tomcat-9/webapps$ mkdir upload
	 ubuntu@ip-172-31-13-122:/opt/tomcat/tomcat-9/webapps$ sudo chown tomcat: upload
	 */
	String FILE_LOCATION = "/opt/tomcat/tomcat-9/webapps/upload";

	void writeProc(MultipartHttpServletRequest multi);

	void viewProc(int no, Model model);

	void boardProc(Model model, int currentPage, String search, String select, HttpServletRequest req);

	void upHit(int no);

	boolean modifyProc(BoardDTO board);

	boolean deleteProc(String pw, String pwOk, String no);

	boolean deletes(String pw, String pwOk, String[] checks);

}
