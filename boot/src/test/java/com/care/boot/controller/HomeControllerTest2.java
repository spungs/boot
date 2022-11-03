package com.care.boot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.care.boot.membership.MemberController;
import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;
import com.care.boot.membership.service.MemberServiceImpl;

@WebMvcTest(controllers = {HomeController.class, MemberController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class HomeControllerTest2 {
	@Autowired private MockMvc mvc;
	@MockBean private MemberServiceImpl service;
	
	private MemberDTO member;
	private PostDTO post;
	@Test
	void test() {
		assertThat(service).isNotNull();
		assertThat(mvc).isNotNull();
	}
	
	@BeforeEach
	void setUp() {
		member = new MemberDTO();
		member.setId("junit");
		member.setPw("1234");
		member.setConfirmPw("1234");
		member.setGender("m");
		member.setEmail("junit@care.com");
	
		
		post = new PostDTO();
		post.setId(member.getId());
		post.setNo(0);
		post.setZipcode("12345");
		post.setAddr1("서울");
		post.setAddr2("101동");
	}
	
	// get 요청, 파라미터x, Model 존재함.
	@Test
	void testIndex() throws Exception {
		mvc.perform(get("/"))
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attribute("formpath", "home"))
		.andExpect(view().name("index"))
		.andDo(print())
		;
	}	
	//post요청 , 많은 파라미터, RedirectAttributes 응답 데이터 있음.
	@Test
	void testMemberProc() throws Exception {
		given(service.memberProc(any(), any())).willReturn("가입 완료");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("id", member.getId());
		params.add("pw", member.getPw());
		params.add("confirmPw", member.getConfirmPw());
		params.add("gender", member.getGender());
		params.add("email", member.getEmail());
		params.add("no", post.getNo()+"");
		params.add("zipcode", post.getZipcode());
		params.add("addr1", post.getAddr1());
		params.add("addr2", post.getAddr2());
		
		mvc.perform(post("/memberProc").params(params))
		.andExpect(status().is3xxRedirection())
		.andExpect(flash().attribute("msg", "가입 완료"))
		.andExpect(view().name("redirect:/index?formpath=home"))
		.andDo(print())
		;
	}	



	
}
