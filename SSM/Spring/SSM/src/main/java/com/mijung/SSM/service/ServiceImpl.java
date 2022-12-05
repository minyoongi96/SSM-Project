package com.mijung.SSM.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.BroadcastingRepository;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.UsersRepository;

@Service
public class ServiceImpl implements SsmService{

	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	OurCategoryRepository ocRepository;

	@Autowired
	BroadcastingRepository bcRepository;
	
//	@Override
//	public Users login(Users userVO) {
//		String userId = userVO.getUserId();
//		String userPw = userVO.getUserPw();
//		
//		Users findUser = userRepository.findByUserId_UserPw(userId, userPw);
//		
//	}
	
	// 입력받은 userId를 가진 사용자 정보 select
	@Override
	public Users findByUserId(Users usersVO) {
		// 입력 받은 아이디 = UserId인 데이터의 아이디를 변수 findUser에 저장
		Users userVO = userRepository.findByUserId(usersVO.getUserId());
		
		return userVO;
	}
	// 세션에 있는 userId참조하여 우리회사 카테고리 정보 가져오기
	@Override
	public List<OurCategory> OcfindAllByUsersVO(Users userVO) {
		return ocRepository.findAllByUsersVO(userVO);
	}


	@Override
	public List<Broadcasting> BcfindAllByUsersVO(Users userVO){
		return bcRepository.findAllByUsersVO(userVO);
	}
}
