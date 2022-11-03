package com.care.boot.ex2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PasswordStrengthMeterTest {
	private PasswordStrengthMeter meter = new PasswordStrengthMeter();

	private void assertStrength(String password, PasswordStrength expStr) {
		PasswordStrength result = meter.meter(password);
		assertThat(result).isEqualTo(expStr);
	}

	@Test // 요구 조건 만족
	void meetsAllCriteria_Then_Strong() {
		assertStrength("ab12!@AB", PasswordStrength.STRONG);
		assertStrength("cd34!@CD", PasswordStrength.STRONG);
	}

	@Test // 7자리 패스워드 테스트
	void otherCriteria_expected_for_Length_Then_NORMAL() {
		assertStrength("ab12!@A", PasswordStrength.NORMAL);
	}

	@Test // 8자리 이상, 숫자 미 포함 시
	void otherCriteria_expected_for_Number_Then_NORMAL() {
		assertStrength("abcde!@A", PasswordStrength.NORMAL);
	}

	@Test // 값이 null 경우
	void nullInput_Then_Invalid() {
		assertStrength(null, PasswordStrength.INVALID);
	}

	@Test // 값이 empty 경우
	void emptyInput_Then_Invalid() {
		assertStrength("", PasswordStrength.INVALID);
	}

	@Test // 대문자가 없는 경우
	void otherCriteria_expected_for_Uppercase_Then_NORMAL() {
		assertStrength("ab12!@cd", PasswordStrength.NORMAL);
	}

	@Test // 대문자만 만족
	void onlyUppercase_Then_Weak() {
		assertStrength("ABCD", PasswordStrength.WEAK);
	}

	@Test // 숫자만 만족
	void onlyNumber_Then_Weak() {
		assertStrength("1234", PasswordStrength.WEAK);
	}

	@Test // 길이만 만족
	void onlyLength_Then_Weak() {
		assertStrength("qwerasdf", PasswordStrength.WEAK);
	}

}
