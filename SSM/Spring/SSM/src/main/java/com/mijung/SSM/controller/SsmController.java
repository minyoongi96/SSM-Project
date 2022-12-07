package com.mijung.SSM.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mijung.SSM.entity.Board;
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
	@RequestMapping("/loginpage.do")
	public String loginpage() {
		return "login";
	}
	
	@PostMapping(value = "/login.do")
	public String loginId(Users user, HttpSession session, Model model){
		if(ssmService.loginCheck(user) == false) {
			return "redirect:/main.do";
		}
		else {
			Users LoginUser = ssmService.findByUserId(user);
			session.setAttribute("user", LoginUser);
			
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
			model.addAttribute("bcList", bcList);
			
			return "listTest";
		}
	}
	
	@GetMapping(value="boardList.do")
	public String boardList(Model model) {
		List<Board> boardList = ssmService.BoardfindAll();
		model.addAttribute("boardList", boardList);
		System.out.println(boardList.toString());
		return "boardList";
	}
	
}
