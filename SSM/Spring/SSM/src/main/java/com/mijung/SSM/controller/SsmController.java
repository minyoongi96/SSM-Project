package com.mijung.SSM.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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
	
	@RequestMapping(value = "/loginCheck.do")
	public String loginId(Users user, HttpSession session, RedirectAttributes rttr){
		if(ssmService.loginCheck(user) == false) {

			return "redirect:/main.do";
		}
		else {
			Users LoginUser = ssmService.findByUserId(user);
			session.setAttribute("user", LoginUser);
			
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
			rttr.addFlashAttribute("bcList", bcList);
			
			return "redirect:/list.do";
		}
	}
	
	// 방송 리스트로 넘어가는 컨트롤러
	@RequestMapping(value = "/list.do")
	public String listpage(Model model, HttpServletRequest request, HttpSession session) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		// 로그인 할 시 
		if(inputFlashMap != null) {
			List<Broadcasting> bcList = (List<Broadcasting>)inputFlashMap.get("bcList");
			model.addAttribute("bcList", bcList);
		}
		//로그인 된 상태에서 넘어갈때
		else {
			Users user = (Users)session.getAttribute("user");
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(user);
			model.addAttribute("bcList", bcList);
		}

		return "broadcast_list";

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
	
	@PostMapping(value="/boardWrite.do")
	public String BoardWrite(Board boardVO, HttpSession session) {
		Users user = (Users) session.getAttribute("user");
		boardVO.setUsersVO(user);
		System.out.println(boardVO);
		boardRepository.save(boardVO);
		return "redirect:/boardList.do";
	}
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {			
		session.invalidate();
		
		return "redirect:/main.do";
	}

}
