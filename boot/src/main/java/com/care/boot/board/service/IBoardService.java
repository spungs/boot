package com.care.boot.board.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.board.dto.BoardDTO;

public interface IBoardService {
	
	String FILE_LOCATION = "C:\\javas\\upload";

	void writeProc(MultipartHttpServletRequest multi);

	void viewProc(int no, Model model);

	void boardProc(Model model, int currentPage, String search, String select, HttpServletRequest req);

	void upHit(int no);

	boolean modifyProc(BoardDTO board);

	boolean deleteProc(String pw, String pwOk, String no);

	boolean deletes(String pw, String pwOk, String[] checks);

}
