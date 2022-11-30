package com.mijung.SSM;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mijung.SSM.entity.Users;
import com.mijung.SSM.repository.UserRepository;
import com.mijung.SSM.service.LoginService;

@SpringBootTest
class SsmApplicationTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginService loginService;

	@Test
	void contextLoads() {
		
	}
	
 //user 테이블 생성 테스트
//	@Test
//	void insertUser() {
//		User vo = new User();
//		vo.setUserId("admin1");
//		vo.setUserPw("1234");
//		vo.setUserCompany("GJAI");
//		
//		userRepository.save(vo);
//	}

	// User 테이블 selectAll 테스트
//	@Test
//	void selectAllUser() {
//		List<Users> list = userRepository.findAll();
//		
//		assertThat(list).isNotEmpty();
//	}
	@Test
	void test1() {
		Users user = new Users();
		user.setUserId("user01");
		user.setUserPw("1q2w3e4r");
		
		Users findUser = userRepository.findByUserId(user.getUserId());
		System.out.println(findUser);
	}
}
