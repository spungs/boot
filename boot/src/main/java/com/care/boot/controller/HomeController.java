package com.care.boot.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/")
	public String index(Model model) {
		model.addAttribute("formpath", "home");
		System.out.println("실행");
		return "index";
	}

	//http://localhost:8085/quiz/index?formpath=board
	@RequestMapping(value = "index")
	public void index(String formpath, Model model, String modifyId, HttpSession session) {
		model.addAttribute("formpath", formpath);
		/*
		 *  수정 버튼에서 보내준 아이디를 세션에 저장하여 modifyCheck(비밀번호) 요청 처리함. 
		 *  if("modifyCheck".equals(formpath) )
		 *  
		 *  세션에 저장된 값이 없으면 동작, 담겨 있다면 동작되지 않음.(비밀번호가 틀린 경우)
		 *  A계정에서 B계정으로 변경해서 수정을 한다면 세션의 modifyId 변경
		 *  if(sessionId == null || sessionId.equals(modifyId) == false)
		 */
		if("modifyCheck".equals(formpath) || "memberDelete".equals(formpath)) {
			String sessionId = (String)session.getAttribute("modifyId");
			if(sessionId == null ||sessionId.equals(modifyId) == false)
				session.setAttribute("modifyId", modifyId);
		}
			
	}
	// index메소드, memberDelete(패스워드 입력), deleteAndCheckProc(패스워드 체크 및 삭제), home
	@RequestMapping(value = "memberDelete")
	public String memberDelete() {
		return "member/memberDeleteForm";
	}
	
	// index메소드, modfiyCheck(패스워드 입력), modifyCheckProc(패스워드 체크), memberModify
	@RequestMapping(value = "modifyCheck")
	public String modifyCheck() {
		return "member/modifyCheckForm";
	}
	
	@RequestMapping(value = "memberModify")
	public String memberModfiy() {
		return "member/memberModifyForm";
	}
	
	@RequestMapping(value = "home")
	public void home() {}
	
	@RequestMapping(value = "login")
	public String login() {
		return "member/loginForm";
	}
	@RequestMapping(value = "member")
	public String member() {
		return "member/memberForm";
	}
	@RequestMapping(value = "board")
	public String board() {
		return "board/boardForm";
	}
	@RequestMapping(value = "memberList")
	public String memberList() {
		return "member/memberListForm";
	}
	
	@RequestMapping(value = "userInfo")
	public String userInfo() {
		return "member/userInfoForm";
	}
}
