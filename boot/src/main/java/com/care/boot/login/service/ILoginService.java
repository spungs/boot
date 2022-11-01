package com.care.boot.login.service;

import com.care.boot.membership.dto.LoginDTO;

public interface ILoginService {
	String loginProc(LoginDTO login);
}
