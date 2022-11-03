package com.care.boot.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.care.boot.membership.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
	@Mock private MemberServiceImpl service;
	@Mock private Model model;
	@Mock private RedirectAttributes ra;
	@InjectMocks private HomeController controller;
	
	@Test
	void test() {
		assertThat(service).isNotNull();
		assertThat(controller).isNotNull();
	}
	
//	@Test
//	void testGetMembers() {
////		given(service.members()).willReturn(new ArrayList<JenkinsDTO>());
//		controller.members(model);
//		then(service).should().members();
//	}
//	@Test
//	void testLogin() {
//		given(service.login("admin", "1234")).willReturn("로그인 성공");
//		controller.login("admin", "1234", model, ra);
//		then(service).should().login("admin", "1234");
//		
//	}	
//
//	@Test
//	void testRegister() {
//		JenkinsDTO member = new JenkinsDTO();
//		member.setId("junit");
//		member.setPw("1234");
//		member.setAddr("서울");
//		member.setFullName("제이유닛");
//		member.setTel("010-1234-1234");
//		
//		given(service.register(member)).willReturn("가입 성공");
//		controller.register(member, model, ra);
//		then(service).should().register(member);
//	}	
}
