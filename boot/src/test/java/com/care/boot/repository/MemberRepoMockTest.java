package com.care.boot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.care.boot.membership.dao.IMemberDAO;
import com.care.boot.membership.dto.LoginDTO;
import com.care.boot.membership.dto.MemberDTO;

@ExtendWith(MockitoExtension.class)
class MemberRepoMockTest {
	@Mock private IMemberDAO repo; 
	private MemberDTO member;
	
	@BeforeEach
	void setUp() {
		member = new MemberDTO();
		member.setId("junit");
		member.setPw("1234");
		member.setConfirmPw("1234");
		member.setGender("m");
		member.setEmail("junit@care.com");
	}
	
	@Test
	void testMemberList() {
		
		given(repo.memberList(1, 3, "id", "admin")).willReturn(new ArrayList<MemberDTO>());
		ArrayList<MemberDTO> result = repo.memberList(1, 3, "id", "admin");
		assertNotNull(result);
	}
	
	@Test
	void testUserInfo() {
		String id = "junit";
		given(repo.userInfo(id)).willReturn(member);
		MemberDTO result = repo.userInfo("junit");
		assertNotNull(result);
	}
	@Test
	void testInserts() {
		LoginDTO login = member;
		given(repo.insertLogin(login)).willReturn(1);
		given(repo.insertMember(member)).willReturn(1);
		int count = repo.insertLogin(login);
		count = repo.insertMember(member);
		assertThat(count).isOne();
	}
	@Test
	void testIsExists() {
		String id = "junit";
		given(repo.isExistId(id)).willReturn(0);
		int count = repo.isExistId("junit");
		assertThat(count).isZero();
	}
	

}
