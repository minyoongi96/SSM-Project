package com.mijung.SSM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Users;


@Repository                                       // 테이블, pk 타입
public interface UsersRepository extends JpaRepository<Users, String> {
	
	
	Users findByUserId(String userId);	// 로그인 : 해당 id를 가지는 유저 확인
	
	@Query(value = "select * from users u where u.user_id = ?1 and u.user_pw = ?2", nativeQuery = true)
	Users findByUserId_UserPw(String userId, String userPw);
	
	
	// save() : insert, update
	// findAll(), findById()   : select * from User , where id=
	// get()
	// @Query("")   : 복잡한 건 직접 쓰기
	
	
}
