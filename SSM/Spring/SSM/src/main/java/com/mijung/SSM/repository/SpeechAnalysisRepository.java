package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.Dto.SttDto;
import com.mijung.SSM.Dto.TimeKeywordDto;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.SpeechAnalysis;

@Repository
public interface SpeechAnalysisRepository extends JpaRepository<SpeechAnalysis, Long>{
	List<SpeechAnalysis> findAllByBroadcastingVO(Broadcasting bc);
	
	@Query(value = "select sa.speechTime " + 
				"from SpeechAnalysis sa " +
				"where sa.speechKeyword = :keyword")
	List<Integer> findTimeAllByKeyword(@Param("keyword") String keyword);

	@Query(value = "select distinct sa.speechKeyword from SpeechAnalysis sa " +
				"where sa.broadcastingVO = :#{#bc}")	
	List<String> findKeywords(@Param("bc") Broadcasting bc);
	
	@Query(value = "select new com.mijung.SSM.Dto.TimeKeywordDto(sa.speechTime, sa.speechKeyword) " + 
			"from SpeechAnalysis sa " +
			"where sa.broadcastingVO = :#{#bc}")
	List<TimeKeywordDto> findKeywordTime(@Param("bc") Broadcasting bc);
	
	
}
