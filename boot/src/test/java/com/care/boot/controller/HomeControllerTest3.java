package com.care.boot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;
import com.care.boot.membership.service.MemberServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest3 {
	@Autowired private MockMvc mvc;
	@Autowired private MemberServiceImpl service;
	
	private MemberDTO member;
	private PostDTO post;
	
	@Test
	void test() {
		assertThat(service).isNotNull();
		assertThat(mvc).isNotNull();
	}

	//get 요청, 파라미터x, 모델 데이터 있음
	
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
	@Test
	void testIndex() throws Exception {
		MvcResult result  = mvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().is(200))
				.andReturn();
		
		ModelAndView content = result.getModelAndView();
		Map<String, Object> map = content.getModel();
		String data = (String)map.get("formpath");
		
		System.out.println("결과 : " + data);
	}
	
	
	//post 요청, 파라미터 있음, RedirectAttributes 에 데이터 있음.
	@Test
	void testMemberProc() throws Exception {
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
		
		mvc.perform(post("/memberProc").params(params).sessionAttr("authStatus", true))
//		.andExpect(status().is2xxSuccessful())
//		.andExpect(flash().attribute("msg", "가입 완료"))
//		.andExpect(view().name("redirect:/index?formpath=home"))
		.andDo(print())
		;
	}	
	
//	//get 요청, 파라미터x
//	@Test
//	void testLogout() throws Exception {
//		mvc.perform(get("/logout"))
//		.andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/"))
////		.andDo(print())
//		;
//	}	
//	
//	// get 요청, 파라미터x, 
//	@Test
//	void testIndex() throws Exception {
//		mvc.perform(get("/"))
//		.andExpect(status().is2xxSuccessful())
//		.andExpect(view().name("index"))
////		.andDo(print())
//		;
//	}	
//	//post 요청, 객체롤 구성된 파라미터 있음, RedirectAttributes 에 데이터 있음
//	@Transactional
//	@Test
//	void testRegister() throws Exception {
//		JenkinsDTO member = new JenkinsDTO();
//		member.setId("asd");
//		member.setPw("1234");
//		member.setAddr("서울");
//		member.setFullName("제이유닛");
//		member.setTel("010-1234-1234");
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//		  params.add("id", member.getId());
//		  params.add("pw", member.getPw());
//		  params.add("fullName", member.getFullName());
//		  params.add("addr", member.getAddr());
//		  params.add("tel", member.getTel());
//		  
//		mvc.perform(post("/register").params(params))
//		.andExpect(status().is3xxRedirection())
//		.andExpect(flash().attribute("msg", "가입 성공"))
//		.andExpect(view().name("redirect:/"))
////		.andDo(print())
//		;
//	}	

}
