package com.mijung.SSM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UserRepository userRepository;
	
	// 로그인 : 해당 유저가 있는지 확인
	@Override
	public Users login(Users user) {
		// 입력 받은 아이디 = UserId인 데이터의 아이디를 변수 findUser에 저장
		Users findUser = userRepository.findByUserId(user.getUserId());
		System.out.println("로그인서비스");
		System.out.println(findUser.toString());
		return findUser;
	}

}
