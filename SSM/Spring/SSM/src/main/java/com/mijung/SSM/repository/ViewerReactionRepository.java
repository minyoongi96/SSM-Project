package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.Dto.KeywordVrCntDto;
import com.mijung.SSM.Dto.PerformanceDto;
import com.mijung.SSM.Dto.SpeechKeywordCateDto;
import com.mijung.SSM.Dto.SttDto;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.ViewerReaction;

@Repository                                       // 테이블, pk 타입
public interface ViewerReactionRepository extends JpaRepository<ViewerReaction, Long> {
	List<ViewerReaction> findAllByBroadcastingVO(Broadcasting bcVO);
	
	//@Query(value = "select SUM(vr.vr_sales) from viewer_reaction vr "
	//+ "where vr.bc_seq = :bcSeq", nativeQuery = true)
	//Integer vrSalesSum(@Param("bcSeq") long bcSeq);
	
	// JPQL 사용
	@Query(value = "select SUM(vr.vrSales) from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc}")
	
	Integer getSalesSum(@Param("bc") Broadcasting bc);
	
	@Query(value = "select AVG(i.itemPrice) " +
			"from Broadcasting bc, Items i " +
			"where i.ourCategoryVO = :#{#oc}")
	Double getPriceAvg(@Param("oc") OurCategory oc);

	@Query(value = "select new com.mijung.SSM.Dto.PerformanceDto(" +
			"SUM(vr.vrBaskets), SUM(vr.vrSales)) " + 
			"from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc}")
	PerformanceDto getPerformance(@Param("bc") Broadcasting bc);
	
	// +3분까지 시청자 이벤트 데이터
	@Query(value = "select new com.mijung.SSM.Dto.SttDto(SUM(vr.vrSales), " +
			"SUM(vr.vrViewers), SUM(vr.vrLookings), SUM(vr.vrBaskets), " +
			"SUM(vr.vrComments), SUM(vr.vrWishlists)) from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc} and vrTimes between :time and (:time + 2)"
			)
	SttDto findAllToSttDto(@Param("bc") Broadcasting bc, @Param("time") int time);
	
	@Query(value = "select new com.mijung.SSM.Dto.KeywordVrCntDto(" +
			"SUM(vr.vrSales), SUM(vr.vrBaskets)) " +
			"from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc}")
	List<KeywordVrCntDto> getKeywordVrCnt(@Param("bc") Broadcasting bc);
	
}
