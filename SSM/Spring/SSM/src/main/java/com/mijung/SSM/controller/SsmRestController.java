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
import com.mijung.SSM.entity.Items;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.service.SsmService;

@RestController
public class SsmRestController {
	@Autowired
	SsmService ssmService;
	
	// 대시보드 화면에 들어갈 모든 데이터셋 보내주는 RestController
	@RequestMapping(value="/Dashboard/{bcSeq}", method=RequestMethod.POST)
	public String mainDashboard(@PathVariable("bcSeq") final Long bcSeq) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Broadcasting bc = ssmService.BcFindByBcSeq(bcSeq);	// bcSeq로 객체 호출
		
		// Map 형태로 받아서 Json 형태로 변환
		Map<Object, Object> info = ssmService.getSalesInfo(bc);
		
		return gson.toJson(info);
	}
	
	@RequestMapping(value="/Dashboard/{bcSeq}", method=RequestMethod.GET)
	public String mainDashboardGET(@PathVariable("bcSeq") final Long bcSeq) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Broadcasting bc = ssmService.BcFindByBcSeq(bcSeq);	// bcSeq로 객체 호출
		
		// Map 형태로 받아서 Json 형태로 변환
		Map<Object, Object> info = ssmService.getSalesInfo(bc);
		
		return gson.toJson(info);
	}
	
	// 아이템 감성점수
	@RequestMapping(value="/itemSentiment/{itemSeq}", method=RequestMethod.GET)
	public String itemSentiment(@PathVariable("itemSeq") final Long itemSeq) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		Map<String, Double> itemSentimentMap = ssmService.itemSentiment(itemSeq);
		
		return gson.toJson(itemSentimentMap);
	}
	
	// 아이템 별점
	@RequestMapping(value="/itemStar/{itemSeq}", method=RequestMethod.GET)
	public String itemStar(@PathVariable("itemSeq") final Long itemSeq) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
		Map<String, Double> itemStarMap = ssmService.itemStar(itemSeq);
			
		return gson.toJson(itemStarMap);
	}
	
}
