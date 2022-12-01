package com.mijung.SSM.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;

public interface SsmService {
//	String login(Users userVO);
	Users findByUserId(Users userVO);
	
	
	List<OurCategory> findAllByUsersVO(Users userVO);
}
