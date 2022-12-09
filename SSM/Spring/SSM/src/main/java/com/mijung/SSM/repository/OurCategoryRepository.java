package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;

@Repository
public interface OurCategoryRepository extends JpaRepository<OurCategory, Long> {
	OurCategory findByOurSeq(long ourSeq);
	
	List<OurCategory> findAllByUsersVO(Users users);
	
}
