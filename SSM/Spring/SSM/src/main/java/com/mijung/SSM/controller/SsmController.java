package com.mijung.SSM.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mijung.SSM.entity.Broadcasting;
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
	
	@PostMapping(value = "/login.do")
<<<<<<< HEAD
	public String loginId(Users user,HttpSession session, Model model){
		Users findUser = ssmService.findByUserId(user);
		// id 일치 확인
		if(findUser == null) return "login";	// **user id가 틀렷을 때, 로그인 실패
		
		// pw 일치 확인
		if(findUser.getUserPw().equals(user.getUserPw())) {    
			session.setAttribute("user", findUser);
			List<Broadcasting> bcList = ssmService.BcfindAllByUsersVO(user);
			model.addAttribute("bcList", bcList);
			return "listpage";	// 로그인 성공
		} else {
			return "login";	// **user pw가 틀렸을 때, 로그인 실패
=======
	public String loginId(Users user, HttpSession session, Model model){
		if(ssmService.loginCheck(user) == false) {
			return "redirect:/main.do";
		}
		else {
			Users LoginUser = ssmService.findByUserId(user);
			session.setAttribute("user", LoginUser);
			
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
			model.addAttribute("bcList", bcList);
			
			return "login";
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
		}
	}
	
	@PostMapping(value = "/logout.do")
	public String logoutId(HttpSession session) {
		session.invalidate();
		return "main";
	}
	
	@PostMapping(value = "/analysis.do")
		public String analysis(Users user, Model model) {
			// 들고 가야할 테이블 : users broadcasting
			return "analysis";
		}
	
	
	
}
