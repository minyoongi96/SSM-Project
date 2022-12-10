package com.mijung.SSM.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mijung.SSM.entity.Board;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.Comments;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.BoardRepository;
import com.mijung.SSM.repository.CommentsRepository;
import com.mijung.SSM.service.SsmService;
import com.mysql.cj.Session;

@Controller
public class SsmController {
	
	@Autowired
	SsmService ssmService;
	@Autowired
	CommentsRepository cmRepository;
	@Autowired
	BoardRepository boardRepository;
	
	@RequestMapping("/main.do")
	public String main() {
		return "real_main";
	}
	@RequestMapping("/loginpage.do")
	public String loginpage() {
		return "login";
	}
	
	@PostMapping(value = "/list.do")
	public String loginId(Users user, HttpSession session, Model model){
		if(ssmService.loginCheck(user) == false) {
<<<<<<< HEAD
			System.out.println("==========================");
			return "redirect:/main.do";
=======
			
			return "redirect:/loginpage.do";
>>>>>>> 044c06c5b3feef380f6f28117b7cd41eb7b970bf
		}
		else {
			Users LoginUser = ssmService.findByUserId(user);
			session.setAttribute("user", LoginUser);
			
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
			model.addAttribute("bcList", bcList);

			return "list";
		}
	}
	
	// boardList 이동

	// 방송정보 갖고 Dashboard로 이동
	@RequestMapping(value = "/loadingDashboard.do/{bcSeq}", method=RequestMethod.GET)
	public String loadingDashboard(@PathVariable("bcSeq") final Long bcSeq, Model model) {
		Broadcasting bcVO = ssmService.BcFindByBcSeq(bcSeq);
		model.addAttribute("bc", bcVO);
		
		return "dashBoard";
	}
	// boardList 게시판
	@GetMapping(value="/boardList.do")
	public String boardList(Model model) {
		// 모든 게시판 테이블 가져오기
		List<Board> boardList = ssmService.BoardfindAll();
		model.addAttribute("boardList", boardList);
		return "boardList";
	}

	
	@GetMapping(value = "/boardView.do/{boardSeq}")
	public String boardView(@PathVariable Long boardSeq, Model model) {
		// get방식으로 boardSeq를 자동입력받고 이에 해당하는 BoardVO 새성
		Board boardVO = ssmService.BoardFindByBoardSeq(boardSeq);
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("comment", comment);
		
		return "boardView";
	}
	
	// 문의 답글 작성
	@GetMapping(value="/insert.do/{boardSeq}")
	public String insertComment(@PathVariable Long boardSeq, Comments commentContent,HttpSession session) {
		Board boardVO = ssmService.BoardFindByBoardSeq(boardSeq);
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		if(comment==null) {
			comment = new Comments();
		}
		comment.setBoardVO(boardVO);
		comment.setCommentContent(commentContent.getCommentContent());
		comment.setUsersVO((Users) session.getAttribute("user"));
		cmRepository.save(comment);
		return "redirect:/boardView.do/"+boardSeq;
	}
	
	@GetMapping(value="/delete.do/{boardSeq}")
	public String boardDelete(@PathVariable Long boardSeq) {
		Board boardVO = ssmService.BoardFindByBoardSeq(boardSeq);
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		cmRepository.delete(comment);
		boardRepository.delete(boardVO);
		
		return "redirect:/boardList.do";
	}
	
	@PostMapping(value="boardWrite.do")
	public String BoardWrite(Board boardVO, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		boardVO.setUsersVO(user);
		System.out.println(boardVO);
		boardRepository.save(boardVO);
		return "redirect:/boardList.do";
	}

}
