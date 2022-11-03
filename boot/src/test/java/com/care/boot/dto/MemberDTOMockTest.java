package com.care.boot.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.care.boot.membership.dto.MemberDTO;

@ExtendWith(MockitoExtension.class)
class MemberDTOMockTest {
	@Mock private MemberDTO dto;
	
	@Test
	void testId() {
		given(dto.getId()).willReturn("user");
		assertEquals("test", dto.getId());
	}
	
	@Test
	void testPw() {
		given(dto.getPw()).willReturn("1234");
		assertEquals("1234", dto.getPw());
	}	
	@Test
	void testConfirmPw() {
		given(dto.getConfirmPw()).willReturn("1234");
		assertEquals("1234", dto.getConfirmPw());
	}
	@Test
	void testGender() {
		given(dto.getGender()).willReturn("m");
		assertEquals("m", dto.getGender());
	}
	@Test
	void testEmail() {
		given(dto.getEmail()).willReturn("junit@care.com");
		assertEquals("junit@care.com", dto.getEmail());
	}

}
