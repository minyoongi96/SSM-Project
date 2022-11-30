package com.mijung.SSM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Users;

import ch.qos.logback.core.net.SyslogOutputStream;

@Repository                                       // 테이블, pk 타입
public interface UserRepository extends JpaRepository<Users, String> {
	
	
	Users findByUserId(String userId);	// 로그인 : 해당 id를 가지는 유저 확인
	
	
	// save() : insert, update
	// findAll(), findById()   : select * from User , where id=
	// get()
	// @Query("")   : 복잡한 건 직접 쓰기
	
	
}
