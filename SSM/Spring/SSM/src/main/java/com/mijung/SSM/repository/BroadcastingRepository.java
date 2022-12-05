package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.Users;

<<<<<<< HEAD
<<<<<<< HEAD

@Repository
public interface BroadcastingRepository extends JpaRepository<Broadcasting, Long>{
	
	Broadcasting findBybcSeq(long bcSeq);
	
	List<Broadcasting> findAllByUsersVO(Users users);
	
=======
=======
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
@Repository
public interface BroadcastingRepository extends JpaRepository<Broadcasting, Long>{
	Broadcasting findByBcSeq(long bcSeq);
	List<Broadcasting> findAllByUsersVO(Users users);
<<<<<<< HEAD
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
=======
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
}
