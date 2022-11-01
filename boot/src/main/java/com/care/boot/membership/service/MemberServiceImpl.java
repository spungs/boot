package com.care.boot.membership.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.care.boot.membership.dao.IMemberDAO;
import com.care.boot.membership.dto.AllDTO;
import com.care.boot.membership.dto.LoginDTO;
import com.care.boot.membership.dto.MemberDTO;
import com.care.boot.membership.dto.PostDTO;

@PropertySource("classpath:adminAccount.properties")
@Service
@Transactional
public class MemberServiceImpl implements IMemberService {
	@Autowired
	private IMemberDAO memberDao;
	@Autowired
	private HttpSession session;

	@Override
	public String isExistId(String id) {
		if (id == null)
			return "아이디를 입력 후 다시 시도하세요.";
		int count = memberDao.isExistId(id);
		if (count == 1)
			return "중복 아이디 입니다.";

		return "사용 가능한 아이디입니다.";
	}

	@Override
	public String memberProc(MemberDTO member, PostDTO post) {
		LoginDTO login = member;

		if (login.getId() == null || login.getId().isEmpty())
			return "아이디를 입력하세요.";

		if (login.getPw() == null || login.getPw().isEmpty())
			return "비밀번호를 입력하세요.";

		if (memberDao.isExistId(login.getId()) > 0)
			return "중복 아이디 입니다.";

		Boolean authStatus = (Boolean) session.getAttribute("authStatus");
		if (authStatus == null || authStatus != true)
			return "이메일 인증 후 가입 할 수 있습니다.";

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String securePw = encoder.encode(login.getPw());
		login.setPw(securePw);

		memberDao.insertLogin(login);

		if ("m".equals(member.getGender()) || "w".equals(member.getGender()) || "n".equals(member.getGender()))
			memberDao.insertMember(member);

		if (!("".equals(post.getZipcode())))
			memberDao.insertPost(post);

		session.invalidate();// 인증번호 및 인증 상태 제거
		return "가입 완료";
	}

	@Override
	public void memberList(int currentPage, String select, String search) {
		int pageBlock = 3; // 한 화면에 보여줄 데이터 수
		int totalCount = memberDao.memberCount(); // 총 데이터의 수 
		int end = currentPage * pageBlock; // 데이터의 끝 번호
		int begin = end+1 - pageBlock; // 데이터의 시작 번호
		
		ArrayList<MemberDTO> list = memberDao.memberList(begin, end, select, search);
		session.setAttribute("list", list);
		String url = "/boot/memberListProc?select=" + select + "&search=" + search + "&currentPage=";
		session.setAttribute("page", PageService.getNavi(currentPage, pageBlock, totalCount, url));
	}

	@Override
	public AllDTO userInfo(String id) {
//		AllDTO user = memberDao.userAll(id);
//		return user;

		MemberDTO member = memberDao.userInfo(id);
		PostDTO post = memberDao.postInfo(id);
		AllDTO user = new AllDTO();
		if (member != null) {
			user.setId(member.getId());
			user.setEmail(member.getEmail());
			user.setGender(member.getGender());
		}
		if (post != null) {
			user.setZipcode(post.getZipcode());
			user.setAddr1(post.getAddr1());
			user.setAddr2(post.getAddr2());
		}
		return user;
	}

	@Override
	public boolean modifyCheckProc(LoginDTO check) {
		if (check.getPw().equals(check.getConfirmPw()) == false) {
			return false;
		}

		// 세션 아이디로 비밀번호 확인(일반사용자 또는 관리자 계정)
		String id = (String) session.getAttribute("id");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		LoginDTO login = memberDao.userPassword(id);
		if (login == null || encoder.matches(check.getPw(), login.getPw()) == false) {
			return false;
		}

		// 수정자 정보 담아 memberModifyForm.jsp 출력하기 위한 데이터
		String modifyId = (String) session.getAttribute("modifyId");
		AllDTO user = userInfo(modifyId);
		user.setPw(login.getPw());
		session.setAttribute("user", user);
		return true;

	}

	/*
	 * ADMIN : 외부 파일 변수 test1 : 실패 시 기본 값
	 */
	@Value("${ADMIN:test1}")
	private String adminAccount;

	@Override
	public String memberModifyProc(AllDTO user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (user.getPw().equals(user.getConfirmPw()) == false) {
			return "두 비밀번호가 일치하지 않습니다.";
		}

		if (user.getPw() != "") {
			LoginDTO login = user;
			String tmp = encoder.encode(user.getPw());
			login.setPw(tmp);
			memberDao.updateLogin(login);
		}

		// 바로 위 modifyCheckProc()에서 입력한 회원 정보.
		AllDTO oldUserInfo = (AllDTO) session.getAttribute("user");
		if (user.getEmail() != "" && user.getEmail().equals(oldUserInfo.getEmail()) == false) {
			MemberDTO member = user;
			memberDao.updateMember(member);
		}

		if (user.getZipcode() != "" && user.getZipcode().equals(oldUserInfo.getZipcode()) == false) {
			PostDTO post = new PostDTO();
			post.setId(user.getId());
			post.setZipcode(user.getZipcode());
			post.setAddr1(user.getAddr1());
			post.setAddr2(user.getAddr2());
			memberDao.updatePost(post);
		}

		// 관리자 계정이 아니라면 세션의 정보는 모두 삭제
		if (adminAccount.equals(session.getAttribute("id")) == false)
			session.invalidate();

		return "회원 수정 완료";
	}

	@Override
	public boolean deleteAndCheckProc(LoginDTO check) {

		if (check.getPw().equals(check.getConfirmPw()) == false) {
			return false;
		}
		
		// 세션 아이디로 비밀번호 확인(일반사용자 또는 관리자 계정)
		String id = (String) session.getAttribute("id");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		LoginDTO login = memberDao.userPassword(id);
		if (login == null || encoder.matches(check.getPw(), login.getPw()) == false) {
			return false;
		}
		
		// 계정 삭제
		String modifyId = (String)session.getAttribute("modifyId");
		memberDao.deleteLogin(modifyId);
		
		// 관리자 계정과 로그인된 계정이 다르거나 관리자 계정과 수정계정이 같으면 세션 삭제
		if(adminAccount.equals(id) == false || adminAccount.equals(modifyId))
			session.invalidate();
		return true;
	}
}










