package com.mijung.SSM.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mijung.SSM.entity.Users;
import com.mijung.SSM.service.LoginService;

@Controller
public class SsmController {
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping("/main.do")
	public String main() {
		return "main";
	}
	
	@GetMapping(value = "/login.do")
	public String login() {
		return "login";
	}
	
	@PostMapping(value = "/login.do")
	public String loginId(Users user, HttpSession session){
		Users findUser = loginService.login(user);
		if(findUser.getUserPw().equals(user.getUserPw())) {     // 아이디 비번이 맞으면 true가 되어 이프문 진행
			session.setAttribute("user", findUser);
			return "main.do";
		} else {
			return "login";
		}
	}
}
