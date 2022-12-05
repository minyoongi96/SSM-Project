package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.entity.ViewerReaction;

import ch.qos.logback.core.net.SyslogOutputStream;

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
	
	
	
	
}
