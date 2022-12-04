package com.mijung.SSM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mijung.SSM.service.SsmService;

@RestController
public class SsmRestController {
	@Autowired
	SsmService ssmService;
	
	public String test() {
		return "";
	}
}
