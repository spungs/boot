package com.care.boot.ex1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CalcProcTest {
	
	@Test
	public void plus() {
		int result = CalcProc.plus(10,20);
		assertThat(result).isEqualTo(320);
	}
}
