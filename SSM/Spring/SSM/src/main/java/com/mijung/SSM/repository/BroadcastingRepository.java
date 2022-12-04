package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.Users;

@Repository
public interface BroadcastingRepository extends JpaRepository<Broadcasting, Long>{
	Broadcasting findByBcSeq(long bcSeq);
	List<Broadcasting> findAllByUsersVO(Users users);
}
