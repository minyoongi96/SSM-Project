package com.mijung.SSM.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.service.SsmService;

@RestController
public class SsmRestController {
	@Autowired
	SsmService ssmService;
	
	// 대시보드 화면에 들어갈 모든 데이터셋 보내주는 RestController
	@RequestMapping(value="/Dashboard/{bcSeq}", method=RequestMethod.GET)
	public String VrSalesSum(@PathVariable("bcSeq") final Long bcSeq) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Broadcasting bc = ssmService.BcFindByBcSeq(bcSeq);	// bcSeq로 객체 호출
		
		// Map 형태로 받아서 Json 형태로 변환
		Map<Object, Object> info = ssmService.getSalesInfo(bc);
		
		return gson.toJson(info);
	}
	
	// 테스트용
//	@RequestMapping(value="/stars/{bcSeq}", method=RequestMethod.GET)
//	public String getStars(@PathVariable("bcSeq") final Long bcSeq) {
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		Broadcasting bc = ssmService.BcFindByBcSeq(bcSeq);
//		
//		List<Object> result = ssmService.getStarsAvgGroupBy(bc);
//		
//		return gson.toJson(result);
//	}
	
}
