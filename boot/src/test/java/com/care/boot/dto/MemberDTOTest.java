package com.care.boot.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.care.boot.membership.dto.MemberDTO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberDTOTest {
	private MemberDTO dto;
	
	public MemberDTOTest() {
		dto = new MemberDTO();
		System.out.println("생성자");
	}
	
	@BeforeEach
	void setUp() {
		System.out.println("@BeforeEach");
		dto.setId("junit");
		dto.setPw("1234");
		dto.setConfirmPw("1234");
		dto.setGender("m");
		dto.setEmail("junit@care.com");
	}
	@Test
	@Order(1)
	void testId() {
		assertEquals(dto.getId(), "junit");
	}
	@Test
	@Order(2)
	void testPw() {
		assertEquals(dto.getPw(), "1234");
	}	
	@Test
	@Order(3)
	void testConfirmPw() {
		assertEquals(dto.getConfirmPw(), "1234");
	}
	@Test
	@Order(4)
	void testEmail() {
		assertEquals(dto.getEmail(), "junit@care.com");
	}
	@Test
	@Order(5)
	void testGender() {
		assertEquals(dto.getGender(), "m");
	}

}
