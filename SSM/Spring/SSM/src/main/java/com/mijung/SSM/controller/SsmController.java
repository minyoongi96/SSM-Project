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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "main";
	}
	@RequestMapping("/loginpage.do")
	public String loginpage() {
		return "login";
	}
	
	@PostMapping(value = "/login.do")
	public String loginId(Users user, HttpSession session, Model model){
		if(ssmService.loginCheck(user) == false) {
			
			return "redirect:/loginpage.do";
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
		// BoardVO에 맞는 댓글리스트 가져옴
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		// boardView에서 사용할 boardVO와 댓글리스트 모델 저장
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("comment", comment);
		return "boardView";
	}
	
	// 문의 답글 작성
	@GetMapping(value="/insert.do/{boardSeq}")
	public String insertComment(@PathVariable Long boardSeq, String commentContent) {
		Board boardVO = ssmService.BoardFindByBoardSeq(boardSeq);
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		
		comment.setBoardVO(boardVO);
		comment.setCommentContent(commentContent);
		// 랑 관리자일때만 답글 작성 보이게 하기!
		Users user = new Users();
		user.setUserId("admin01");
		comment.setUsersVO(user);
		cmRepository.save(comment);
		return "redirect:/boardView.do/"+boardSeq;
	}
	
	@GetMapping(value="/delete.do/{boardSeq}")
	public String deleteBoard(@PathVariable Long boardSeq) {
		Board boardVO = ssmService.BoardFindByBoardSeq(boardSeq);
		Comments comment = ssmService.CmFindByBoardVO(boardVO);
		cmRepository.delete(comment);
		boardRepository.delete(boardVO);
		
		return "redirect:/boardList.do";
	}
	
	@GetMapping(value="boardWrite.do")
	public String WriteBoard(Board boardVO) {
		System.out.println(boardVO); // 확인 해보고.. save해보자..!! 
//		boardRepository.save(boardVO);
		return "redirect:/boardList.do";
	}
	
}
