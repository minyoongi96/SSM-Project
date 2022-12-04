package com.mijung.SSM.service;

import java.util.List;

import javax.transaction.Transactional;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;

public interface SsmService {
//	String login(Users userVO);
	Users findByUserId(Users userVO);
	Boolean loginCheck(Users userVO);
	List<OurCategory> OcfindAllByUsersVO(Users userVO);
	List<Broadcasting> BcFindAllByUsersVO(Users user);
	Broadcasting findByBcSeq(Long bcSeq);
}
