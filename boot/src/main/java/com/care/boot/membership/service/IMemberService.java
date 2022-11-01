package com.care.boot.membership.service;


import com.care.boot.membership.dto.AllDTO;
import com.care.boot.membership.dto.LoginDTO;
import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;

public interface IMemberService {

	// 중복 확인
	public String isExistId(String id);
	
	// 회원 가입
	public String memberProc(MemberDTO member, PostDTO post);

	//회원 목록
	public void memberList(int currentPage, String select, String search);

	//회원 정보
	public AllDTO userInfo(String id);
	
	// 비밀번호 체크
	public boolean modifyCheckProc(LoginDTO check);

	// 회원 정보 수정
	public String memberModifyProc(AllDTO user);

	// 삭제 및 비밀번호 체크
	public boolean deleteAndCheckProc(LoginDTO check);
	
}
