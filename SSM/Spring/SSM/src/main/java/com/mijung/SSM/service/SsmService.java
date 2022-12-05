package com.mijung.SSM.service;

import java.util.List;
import java.util.Map;

import com.mijung.SSM.Dto.StarDto;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.entity.ViewerReaction;

public interface SsmService {
	Users findByUserId(Users userVO);
	Boolean loginCheck(Users userVO);
	List<OurCategory> OcFindAllByUsersVO(Users userVO);
	List<Broadcasting> BcFindAllByUsersVO(Users user);
	Broadcasting BcFindByBcSeq(Long bcSeq);
	List<ViewerReaction>  VcFindAllByBroadcastingVO(Broadcasting bcVO);
	
	// RestController 서비스
	// 방송 상세정보 -> salesCnt(총 결제 수), salesPred(예상 판매액)
	Map<Object, Object> getSalesInfo(Broadcasting bc);
	
	// 아이템 리스트 -> [{item_seq:, item_name: , starAvg : {카테고리 별 평점}}] 형태 
	List<Object> getStarsAvgGroupBy(Broadcasting bc);
}
