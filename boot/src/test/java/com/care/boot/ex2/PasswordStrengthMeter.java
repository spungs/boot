package com.care.boot.ex2;

public class PasswordStrengthMeter {

	public PasswordStrength meter(String pw) {
		if (pw == null || pw == "")	return PasswordStrength.INVALID;
		
		int metCounts = 0;
		if(pw.length() >= 8) { metCounts++; }
		if(meetsContainigNumberCriteria(pw)) { metCounts++; }
		if(meetsContainigUppercaseCriteria(pw)) { metCounts++; }
		if(metCounts == 1) { return PasswordStrength.WEAK; }
		if(metCounts == 2) { return PasswordStrength.NORMAL; }
		
		return PasswordStrength.STRONG;
	}

	private boolean meetsContainigUppercaseCriteria(String str) {
		for (char ch : str.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				return true;
			}
		}
		return false;
	}

	private boolean meetsContainigNumberCriteria(String pw) {
		for (char tmp : pw.toCharArray()) {
			System.out.println(tmp);
			if (tmp >= '0' && tmp <= '9') {
				return true;
			}
		}
		return false;
	}

}
