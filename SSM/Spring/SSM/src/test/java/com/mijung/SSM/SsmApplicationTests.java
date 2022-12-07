package com.mijung.SSM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mijung.SSM.Dto.SttDto;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.SpeechAnalysis;
import com.mijung.SSM.repository.BroadcastingRepository;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.SpeechAnalysisRepository;
import com.mijung.SSM.repository.UsersRepository;
import com.mijung.SSM.repository.ViewerReactionRepository;
import com.mijung.SSM.service.SsmService;

@SpringBootTest
class SsmApplicationTests {
	
	@Autowired
	SsmService ssmService;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OurCategoryRepository ocRepository;
	
	@Autowired
	BroadcastingRepository bcRepository;
	
	@Autowired
	SpeechAnalysisRepository saRepository;
	
	@Autowired
	ViewerReactionRepository vrRepository;
	
//	@Transactional
//	@Test
//	void LoginTest() {
//		Users usersVO = new Users();
//		usersVO.setUserId("admin01");
//		usersVO.setUserPw("1q2w3e");
//		
//		// Logincheck
//		if(ssmService.loginCheck(usersVO)) {
//			Users LoginUser = ssmService.findByUserId(usersVO);
//			
//			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
//			for(Broadcasting bc : bcList) {
//				System.out.println(bc);
//			}
//		}
//		else {
//			System.out.println("Login Failed");
//		}
//	}
	
//	@Transactional
//	@Test
//	void getBcTest() {
//		JsonObject obj = new JsonObject();
//		Long bcSeq = 2L;
//		Broadcasting bc = ssmService.BcFindByBcSeq(bcSeq);
//
//		obj.addProperty("bc_seq", bc.getBcSeq());
//		obj.addProperty("user_id", bc.getUsersVO().getUserId());
//		obj.addProperty("our_seq", bc.getOurCategoryVO().getOurSeq());
//		obj.addProperty("bc_title", bc.getBcTitle());
//		obj.addProperty("bc_male", bc.getBcMale());
//		obj.addProperty("bc_female", bc.getBcFemale());
//		
//		
//		System.out.println(obj.toString());
//	}
	
//	@Transactional
//	@Test
//	void getVcListTest() {
//		Broadcasting bcVO = ssmService.BcFindByBcSeq(1L);
//		System.out.println(ssmService.getSalesInfo(bcVO));
//	}
	
//	@Transactional
//	@Test
//	void getSttDataTest() {
//		Broadcasting bc = bcRepository.findByBcSeq(1L);
//		
//		// 해당 방송에 나온 키워드 모음
//		List<String> keywordList = saRepository.findKeywords(bc);
//		List<Object> list = new ArrayList<Object>();
//		
//		// 나온 모든 키워드 반복
//		for(String keyword : keywordList) {
//			// 해당 키워드가 나온 시간들 모음
//			List<Integer> timeList = saRepository.findTimeAllByKeyword(keyword);
//			
//			for(Integer time : timeList) {
//				Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//				SttDto dto = vrRepository.findAllToSttDto(bc, time);
//				map.put("keyword", keyword);
//				map.put("speech_time", time);
//				map.put("Cnt", dto);
//				
//				list.add(map);
//				
//			}
//		}
//		System.out.println(list);
//		
//	}

}
