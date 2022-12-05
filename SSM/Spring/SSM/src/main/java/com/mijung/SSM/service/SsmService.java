package com.mijung.SSM.service;

import java.util.List;
import java.util.Map;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.transaction.Transactional;

=======
import com.mijung.SSM.Dto.StarDto;
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
=======
import com.mijung.SSM.Dto.StarDto;
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
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
	
<<<<<<< HEAD
<<<<<<< HEAD
	List<OurCategory> OcfindAllByUsersVO(Users userVO);
	// select * from Our_categories 
	
//	 userId를 참조하여 방송정보 가져오기
	List<Broadcasting> BcfindAllByUsersVO(Users userVO);
=======
	// 아이템 리스트 -> [{item_seq:, item_name: , starAvg : {카테고리 별 평점}}] 형태 
	List<Object> getStarsAvgGroupBy(Broadcasting bc);
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
=======
	// 아이템 리스트 -> [{item_seq:, item_name: , starAvg : {카테고리 별 평점}}] 형태 
	List<Object> getStarsAvgGroupBy(Broadcasting bc);
>>>>>>> 46a3e717453d674f4acb1fd719d361080cc66b55
}
