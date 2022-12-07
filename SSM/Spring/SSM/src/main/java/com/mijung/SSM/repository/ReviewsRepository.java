package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.Dto.SentimentDto;
import com.mijung.SSM.Dto.StarDto;
import com.mijung.SSM.entity.Items;
import com.mijung.SSM.entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
	
//	@Query(value = "select new com.mijung.SSM.Dto.StarDto(" +
//			"r.itemsVO, r.reviewCategory, AVG(r.reviewStar)) " +
//			"from Reviews r, Items i, OurCategory o " +
//			"where o.ourSeq = :#{#bc.bcSeq} " + 
//			"and i.ourCategoryVO = :#{#bc.ourCategoryVO} " +
//			"and i = r.itemsVO group by r.itemsVO, r.reviewCategory")
//	List<StarDto> getSalesStarAverage(@Param("bc") Broadcasting bc);
//	
//	@Query(value = "select new com.mijung.SSM.Dto.StarDto(" +
//			"r.itemsVO, r.reviewCategory, AVG(r.reviewStar)) " +
//			"from Reviews r, Items i " +
//			"where i.ourCategoryVO = :#{#oc} and i = r.itemsVO " +
//			"group by r.itemsVO, r.reviewCategory")
//	List<StarDto> getSalesStarAverage2(@Param("oc") OurCategory oc);
	
	
	
	
	// 제품 별로 평점 평균 구하기
	@Query(value = "select new com.mijung.SSM.Dto.StarDto(r.reviewCategory, AVG(r.reviewStar)) " +
			"from Reviews r " +
			"where r.itemsVO = :#{#item} group by r.reviewCategory")
	List<StarDto> getStarAvg(@Param("item") Items item);
	
	// 제품 별로
	@Query(value = "select new com.mijung.SSM.Dto.SentimentDto(r.reviewCategory, AVG(r.reviewSentiment)) " +
			"from Reviews r " +
			"where r.itemsVO = :#{#item} group by r.reviewCategory")
	List<SentimentDto> getSentimentAvg(@Param("item") Items item);

	List<Reviews> findAllByItemsVO(Items item);
}
