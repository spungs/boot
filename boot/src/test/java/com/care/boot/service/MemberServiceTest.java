package com.care.boot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.care.boot.membership.dto.AllDTO;
import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;
import com.care.boot.membership.service.MemberServiceImpl;

@SpringBootTest
class MemberServiceTest {
	@Autowired private MemberServiceImpl service;
	@Autowired private HttpSession session;
	
	@Test
	void testService() {
		assertNotNull(service);
	}

	@Transactional
	@Test
	void testMemberProc() {
		MemberDTO member = new MemberDTO();
		member.setId("junit");
		member.setPw("1234");
		member.setConfirmPw("1234");
		member.setGender("m");
		member.setEmail("junit@care.com");
		
		PostDTO post = new PostDTO();
		post.setId(member.getId());
		post.setNo(0);
		post.setZipcode("12345");
		post.setAddr1("서울");
		post.setAddr2("101동");
		
		session.setAttribute("authStatus", true);
		String result = service.memberProc(member, post);
		assertEquals("가입 완료", result);
	}
	@Test
	void testUserInfo() {
		String id = "admin";
		AllDTO all = service.userInfo(id);
		System.out.println("이메일 : " + all.getEmail());
		System.out.println("주소 : " + all.getAddr1() + " "+all.getAddr2());
		assertNotNull(all);
	}
}	
