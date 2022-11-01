package com.care.boot.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.care.boot.membership.dto.LoginDTO;

@Mapper
public interface ILoginDAO {
	LoginDTO loginProc(LoginDTO login);
}
