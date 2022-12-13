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
	List<ViewerReaction> findAllByBroadcastingVO(Broadcasting bcVO);	// JPA에서 제공하는 기본 메서
	
	// 복잡하거나 연산이 필요한 쿼리는 JPQL 사용해서 객체지향적으로 
	@Query(value = "select SUM(vr.vrSales) from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc}")
	
	Integer getSalesSum(@Param("bc") Broadcasting bc);
	
	@Query(value = "select AVG(i.itemPrice) " +
			"from Broadcasting bc, Items i " +
			"where i.ourCategoryVO = :#{#oc}")
	Double getPriceAvg(@Param("oc") OurCategory oc);
	
	// select 결과를 DTO 클래스로 반환 
	@Query(value = "select new com.mijung.SSM.Dto.PerformanceDto(" +
			"SUM(vr.vrBaskets), SUM(vr.vrSales)) " + 
			"from ViewerReaction vr " +
			"where vr.broadcastingVO = :#{#bc}")
	PerformanceDto getPerformance(@Param("bc") Broadcasting bc);
	
	// +3분까지 시청자 이벤트 데이터, 방송 데이터와 시청자 이벤트 데이터 이너조인 
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
