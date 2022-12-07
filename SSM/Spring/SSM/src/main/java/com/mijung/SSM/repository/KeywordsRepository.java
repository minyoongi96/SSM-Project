package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mijung.SSM.entity.ViewerReaction;

@Repository                                       // 테이블, pk 타입
public interface KeywordsRepository extends JpaRepository<ViewerReaction, Long> {

	@Query(value = "select k.keywordCategory from Keywords k where keywordName = :keyword")
	String findCategory(@Param("keyword") String keyword);
}