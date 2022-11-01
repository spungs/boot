package com.care.boot.board.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.board.dto.BoardDTO;
import com.care.boot.board.service.IBoardService;


@Controller
public class BoardController {
	final static Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired IBoardService service;
	
	@RequestMapping(value = "writeProc")
	public String writeProc(MultipartHttpServletRequest multi) {
		service.writeProc(multi);
		return "forward:boardProc";
	}
	
	@RequestMapping(value = "boardProc")
	public String boardProc(Model model, 
			@RequestParam(value="currentPage", required = false, defaultValue = "1")int currentPage,
			String search, String select, HttpServletRequest req ) {
		service.boardProc(model, currentPage, search, select, req);
		return "forward:/index?formpath=board";
	}
	
	@RequestMapping(value = "viewProc")
	public String viewProc(@RequestParam(value = "writeNo", required = false) String writeNo, Model model) {
		if(writeNo == null || writeNo == "")
			return "forward:/index?formpath=board";
		
		int no = Integer.parseInt(writeNo);
		service.viewProc(no, model);
		service.upHit(no);
		return "forward:/index?formpath=view";
	}
	
	@RequestMapping(value = "download")
	public void download(@RequestParam(value="fileName") String fileName, HttpServletResponse res) throws Exception {
		if(fileName == "" || "파일 없음".equals(fileName))
			return ;
			
		res.addHeader("Content-disposition", "attachment; filename="+fileName);
		File file = new File(IBoardService.FILE_LOCATION + "\\" + fileName);
		FileInputStream input = new FileInputStream(file);
		FileCopyUtils.copy(input, res.getOutputStream());
		input.close();
		
	}
	
	@RequestMapping(value = "/modifyProc")
	public String modifyProc(BoardDTO board) {
		boolean check = service.modifyProc(board);
		if(check == false) {
			return "forward:index?formpath=modify";
		}
		return "forward:boardProc";
	}
	
	@RequestMapping(value = "deleteProc")
	public String deleteProc(String pw, String pwOk, String no) {
		boolean check = service.deleteProc(pw, pwOk, no);
		if(check == false) {
			return "redirect:delete";
		}
		return "forward:boardProc";
	}
	
	@RequestMapping(value = "deletes")
	public String deletes(String[] checks,String pw, String pwOk ) {
		boolean check = service.deletes(pw, pwOk, checks);
		if(check == false) {
			return "redirect:delete";
		}
		return "forward:boardProc";
	}
}
