package com.care.boot.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.care.boot.board.dto.BoardDTO;

@Controller
public class BoardHomeController {
	
	@Autowired HttpSession session;
	public String checkSession(String url) {
		String id = (String)session.getAttribute("id");
		if(id == null)
			return "member/loginForm";
		return url;
	}
	
	@RequestMapping(value = "/view")
	public String view() {
		String url = checkSession("board/viewForm");
		return url;
	}
	@RequestMapping(value = "/write")
	public String write() {
		String url = checkSession("board/writeForm");
		return url;
	}

	@RequestMapping(value = "modify")
	public String modify(Model model, BoardDTO board) {
		String url = checkSession("board/modifyForm");
		model.addAttribute("board", board); //modifyForm.jsp에서 출력할 데이터
		return url;
	}

	@RequestMapping(value = "delete")
	public String delete(Model model, String proc, HttpServletRequest req) {
		String url = checkSession("board/deleteForm");
		System.out.println("delete proc : " + proc);
		if(proc == null) {
			return "forward:boardProc";
		}
		
		if(proc.equals("deleteProc")) {
			model.addAttribute("no", req.getParameter("no"));
		}else {
			model.addAttribute("checks", req.getParameterValues("checks"));
		}
		
		model.addAttribute("proc", proc);
		return url;
	}
}
