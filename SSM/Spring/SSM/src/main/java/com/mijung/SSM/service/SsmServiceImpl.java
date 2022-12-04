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
public class SsmServiceImpl implements SsmService{

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
	
	@Override
	public Users findByUserId(Users usersVO) {
		// 입력 받은 아이디 = UserId인 데이터의 아이디를 변수 findUser에 저장
		Users userVO = userRepository.findByUserId(usersVO.getUserId());
		
		return userVO;
	}
	
	@Override
	public Boolean loginCheck(Users userVO) {
		Users findUser = userRepository.findByUserId(userVO.getUserId());
		
		// 존재하는 ID인지 확인
		if(findUser == null) {
			return false;
		}
		else {
			// 입력한 PW가 일치한지 확인
			if(findUser.getUserPw().equals(userVO.getUserPw())) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public List<OurCategory> OcfindAllByUsersVO(Users userVO) {
		return ocRepository.findAllByUsersVO(userVO);
	}

	@Override
	public List<Broadcasting> BcFindAllByUsersVO(Users userVO) {
		return bcRepository.findAllByUsersVO(userVO);
	}

	@Override
	public Broadcasting findByBcSeq(Long bcSeq) {
		return bcRepository.findByBcSeq(bcSeq);
	}
}
