package com.care.boot.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.care.boot.membership.dao.IMemberDAO;
import com.care.boot.membership.dto.LoginDTO;
import com.care.boot.membership.dto.MemberDTO;

@SpringBootTest
class MemberRepoTest {
	@Autowired private IMemberDAO repo; 

	@Test
	void testRepo() {
		assertNotNull(repo);
	}
	@Test
	void testMemberList() {
		ArrayList<MemberDTO> members = repo.memberList(1, 3, "id", "admin");
		assertNotNull(members);
	}
	@Test
	void testUserInfo() {
		String id = "admin";
		MemberDTO member = repo.userInfo(id);
		System.out.println("비밀번호 : " + member.getPw());
		System.out.println("이메일 : " + member.getEmail() );
		assertNotNull(member);
	}
	@Test
	void testIsExist() {
		int count = repo.isExistId("admin1");
		assertThat(count).isZero();
	}
	
	@Transactional
	@Test
	void testInserts() {
		MemberDTO member = new MemberDTO();
		member.setId("junit");
		member.setPw("1234");
		member.setConfirmPw("1234");
		member.setGender("m");
		member.setEmail("junit@care.com");
		LoginDTO login = member;
		
		int count = repo.insertLogin(login);
		assertThat(count).isOne();
		count = repo.insertMember(member);
		assertThat(count).isOne();
	}
}
