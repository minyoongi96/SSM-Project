package com.mijung.SSM.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;

public interface SsmService {
//	String login(Users userVO);
	Users findByUserId(Users userVO);
	
	
	List<OurCategory> OcfindAllByUsersVO(Users userVO);
	// select * from Our_categories 
	
//	 userId를 참조하여 방송정보 가져오기
	List<Broadcasting> BcfindAllByUsersVO(Users userVO);
}
