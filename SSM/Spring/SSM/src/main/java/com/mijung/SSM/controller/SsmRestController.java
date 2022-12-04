package com.mijung.SSM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.service.SsmService;

@RestController
public class SsmRestController {
	@Autowired
	SsmService ssmService;
	
	@RequestMapping(value="Broadcasting/{bcSeq}", method=RequestMethod.GET)
	public String BcRest(@PathVariable("bcSeq") final Long bcSeq) {
		JsonObject obj = new JsonObject();
		Broadcasting bc = ssmService.findByBcSeq(bcSeq);
		
		if(bc == null) {
			return null;
		}
		
		obj.addProperty("bc_seq", bc.getBcSeq());
		obj.addProperty("user_id", bc.getUsersVO().getUserId());
		obj.addProperty("our_seq", bc.getOurCategoryVO().getOurSeq());
		obj.addProperty("bc_title", bc.getBcTitle());
		obj.addProperty("bc_male", bc.getBcMale());
		obj.addProperty("bc_female", bc.getBcFemale());
		return obj.toString();
	}
	
	@RequestMapping(value="/salesSum/{bcSeq}", method=RequestMethod.GET)
	public String VrSalesSumRest(@PathVariable("bcSeq") final Long bcSeq) {
		
		
		
		return "";
	}
}
