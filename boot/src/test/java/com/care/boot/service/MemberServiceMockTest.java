package com.care.boot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.care.boot.membership.dao.IMemberDAO;
import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;
import com.care.boot.membership.service.MemberServiceImpl;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockTest {
	@Mock private IMemberDAO repo;
	@Mock private HttpSession session;
	@InjectMocks private MemberServiceImpl service;
	
	private MemberDTO member;
	private MemberDTO member2;
	private ArrayList<MemberDTO> members;
	@BeforeEach
	void setup() {
		member = new MemberDTO();
		member.setId("junit");
		member.setPw("1234");
		member.setConfirmPw("1234");
		member.setGender("m");
		member.setEmail("junit@care.com");
		
		member2 = new MemberDTO();
		member2.setId("admin");
		member2.setPw("1111");
		member2.setConfirmPw("1111");
		member2.setGender("m");
		member2.setEmail("admin@care.com");
		
		
		members = new ArrayList<MemberDTO>();
		members.add(member);
		members.add(member2);
	}
	@Test
	void testService() {
		assertThat(service).isNotNull();
	}
	
	@Test
	void testMemberList() {
		given(repo.memberList(1, 3, "id", "admin")).willReturn(members);
		service.memberList(1, "id", "admin");
		then(repo).should().memberList(1, 3, "id", "admin");
	}

	@Test
	void testInserts() {
		PostDTO post = new PostDTO();
		post.setId(member.getId());
		post.setNo(0);
		post.setZipcode("12345");
		post.setAddr1("서울");
		post.setAddr2("101동");
		
		given(repo.isExistId(member.getId())).willReturn(0);
		service.memberProc(member, post);
		then(repo).should().isExistId("junit");
	}
	@Test
	void userInfo() {
		given(repo.userInfo("admin")).willReturn(member);
		service.userInfo("admin");
		then(repo).should().userInfo("admin");
	}
}	
