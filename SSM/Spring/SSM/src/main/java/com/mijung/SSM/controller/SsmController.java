package com.mijung.SSM.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mijung.SSM.entity.Users;
import com.mijung.SSM.service.SsmService;

@Controller
public class SsmController {
	
	@Autowired
	SsmService ssmService;
	
	@RequestMapping("/main.do")
	public String main() {
		return "main";
	}
	
//	@GetMapping(value = "/login.do")
//	public String login() {
//		return "login";
//	}
	
	@PostMapping(value = "/login.do")
	public String loginId(Users user, HttpSession session){
		Users findUser = ssmService.findByUserId(user);
		// id 일치 확인
		if(findUser == null) return "login";	// **user id가 틀렷을 때, 로그인 실패
		
		// pw 일치 확인
		if(findUser.getUserPw().equals(user.getUserPw())) {     // 아이디 비번이 맞으면 true가 되어 이프문 진행
			session.setAttribute("user", findUser);
			return "main";	// 로그인 성공 
		} else {
			return "login";	// **user pw가 틀렸을 때, 로그인 실패
		}
	}
}
