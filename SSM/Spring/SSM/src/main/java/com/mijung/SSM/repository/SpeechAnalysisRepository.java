package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.Dto.SpeechKeywordCateDto;
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
	
	// select 결과를 DTO 클래스로 반환 
	@Query(value = "select new com.mijung.SSM.Dto.TimeKeywordDto(sa.speechTime, sa.speechKeyword) " + 
			"from SpeechAnalysis sa " +
			"where sa.broadcastingVO = :#{#bc}")
	List<TimeKeywordDto> findKeywordTime(@Param("bc") Broadcasting bc);
	
	// 방송에 나온 SpeechAnalysis객체의 time, keyword와 Keyword객체의 category 이너조인
	@Query(value = "select new com.mijung.SSM.Dto.SpeechKeywordCateDto"
			+ "(sa.speechTime, sa.speechKeyword, k.keywordCategory) "
			+ "from SpeechAnalysis sa left outer join Keywords k on "
			+ "sa.speechKeyword = k.keywordName where sa.broadcastingVO = :#{#bc} "
			+ "and sa.speechTime < 58")
	List<SpeechKeywordCateDto> getSpeechKeywordCate(@Param("bc") Broadcasting bc);
	
	
}
