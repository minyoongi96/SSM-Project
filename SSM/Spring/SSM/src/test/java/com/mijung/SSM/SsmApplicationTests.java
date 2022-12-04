package com.mijung.SSM;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.UsersRepository;
import com.mijung.SSM.service.SsmService;

@SpringBootTest
class SsmApplicationTests {
	
	@Autowired
	SsmService ssmService;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OurCategoryRepository ocRepository;
	
//	@Test
//	void test1() {
//		Users user = new Users();
//		user.setUserId("admin01");
//		user.setUserPw("1q2w3e");
//		
//		Users findUser = ssmService.findByUserId(user);
//		System.out.println(findUser);
//	}
//	
//	@Transactional
//	@Test
//	void test2() {
//		Users usersVO = usersRepository.findByUserId("admin01");
//		
//		List<OurCategory> oc = ocRepository.findAllByUsersVO(usersVO);
//		System.out.println(oc);
//	}
	
	@Transactional
	@Test
	void Logintest() {
		Users usersVO = new Users();
		usersVO.setUserId("admin01");
		usersVO.setUserPw("1q2w3e");
		
		// Logincheck
		if(ssmService.loginCheck(usersVO)) {
			Users LoginUser = ssmService.findByUserId(usersVO);
			
			List<Broadcasting> bcList = ssmService.BcFindAllByUsersVO(LoginUser);
			for(Broadcasting bc : bcList) {
				System.out.println(bc);
			}
		}
		else {
			System.out.println("Login Failed");
		}
	}
}
