package com.care.boot.board.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.boot.board.dto.BoardDTO;
import com.care.boot.board.repository.IBoardDAO;
import com.care.boot.membership.dao.IMemberDAO;
import com.care.boot.membership.dto.LoginDTO;
import com.care.boot.membership.service.PageService;

@Service
public class BoardServiceImpl implements IBoardService{
	@Autowired IBoardDAO mapper;
	
	@Override
	public void writeProc(MultipartHttpServletRequest req) {
		String id = (String)session.getAttribute("id");
		String title = req.getParameter("title");
		String content= req.getParameter("content");
		
		BoardDTO board = new BoardDTO();
		board.setId(id);		board.setTitle(title);
		board.setContent(content);		board.setHit(0);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		board.setWriteDate(sdf.format(date));
		
		MultipartFile file = req.getFile("fileName");
		if(file.getSize() != 0) {
			Calendar cal = Calendar.getInstance();
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			String fileName = sdf.format(cal.getTime()) + file.getOriginalFilename();
			board.setFileName(fileName);
			File save = new File(IBoardService.FILE_LOCATION + "\\" + fileName);
			try {
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			board.setFileName("파일 없음");
		}
		mapper.writeProc(board);
	}

	@Override
	public void boardProc(Model model, int currentPage, String search, String select, HttpServletRequest req) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("select", select);
		
		int totalCount = mapper.boardCount(map);
		int pageBlock = 3;
		int end = currentPage * pageBlock;
		int begin = end+1 - pageBlock;

		ArrayList<BoardDTO> boardList = mapper.boardProc(begin, end, select, search);
		model.addAttribute("boardList", boardList);

		String url = req.getContextPath() + "/boardProc?";
		if(select != null) {
			url+="select="+select+"&";
			url+="search="+search+"&";	
		}
			url+="currentPage=";
		model.addAttribute("page", PageService.getNavi(currentPage, pageBlock, totalCount, url));
	}

	@Override
	public void viewProc(int no, Model model) {
		BoardDTO board = mapper.viewProc(no);
		model.addAttribute("board", board);
	}
	
	@Override
	public void upHit(int no) {
		mapper.upHit(no);
	}

	@Override
	public boolean modifyProc(BoardDTO board) {
		int result = mapper.modifyProc(board);
		if(result == 0)
			return false;
		return true;
	}
	
	@Autowired IMemberDAO memberDao;
	@Autowired HttpSession session;
	
	private boolean pwcheck(String pw, String pwOk) {
		if(pw == "" || pw == null)
			return false;
	
		// 회원DB에서 게시글 작성자의 PW와 입력 패스워드 비교 
		LoginDTO check = memberDao.userPassword((String)session.getAttribute("id"));
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		
		if(check == null || encode.matches(pw, check.getPw()) == false)
			return false;
		return true;
	}
	
	private void deleteImage(String fileName) {
		File deleteFile = new File(FILE_LOCATION+"/"+fileName);
		deleteFile.delete();
	}

	@Override
	public boolean deleteProc(String pw, String pwOk, String n) {
		boolean check = pwcheck(pw, pwOk);
		if(check == false)
			return false;
		
		int no = Integer.parseInt(n);
		
		String fileName = mapper.getFileName(no);
		int result = mapper.deleteProc(no);
		if(result == 0)
			return false;
		deleteImage(fileName);
		return true;
	}
	
	@Override
	public boolean deletes(String pw, String pwOk, String[] checks) {
		boolean check = pwcheck(pw, pwOk);
		if(check == false)
			return false;
		if(checks == null) 
			return false;
		
		for(String n : checks) {
			int no = Integer.parseInt(n);
			String fileName = mapper.getFileName(no);
			mapper.deleteProc(no);
			deleteImage(fileName);
		}

		return true;
	}


}
