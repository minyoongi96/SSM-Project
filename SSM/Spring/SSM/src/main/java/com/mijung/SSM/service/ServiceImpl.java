package com.mijung.SSM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.UsersRepository;

@Service
public class ServiceImpl implements SsmService{

	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	OurCategoryRepository ocRepository;

//	@Override
//	public Users login(Users userVO) {
//		String userId = userVO.getUserId();
//		String userPw = userVO.getUserPw();
//		
//		Users findUser = userRepository.findByUserId_UserPw(userId, userPw);
//		
//	}
	
	@Override
	public Users findByUserId(Users usersVO) {
		// 입력 받은 아이디 = UserId인 데이터의 아이디를 변수 findUser에 저장
		Users userVO = userRepository.findByUserId(usersVO.getUserId());
		
		return userVO;
	}

	@Override
	public List<OurCategory> findAllByUsersVO(Users userVO) {
		return ocRepository.findAllByUsersVO(userVO);
	}

	

}
