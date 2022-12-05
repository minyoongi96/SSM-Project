package com.mijung.SSM.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mijung.SSM.Dto.StarDto;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.Items;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.Reviews;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.entity.ViewerReaction;
import com.mijung.SSM.repository.BroadcastingRepository;
import com.mijung.SSM.repository.ItemsRepository;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.ReviewsRepository;
import com.mijung.SSM.repository.UsersRepository;
import com.mijung.SSM.repository.ViewerReactionRepository;

@Service
public class SsmServiceImpl implements SsmService{

	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	OurCategoryRepository ocRepository;
	
	@Autowired
	BroadcastingRepository bcRepository;
	
	@Autowired
	ViewerReactionRepository vrRepository;
	
	@Autowired
	ReviewsRepository rRepository;
	
	@Autowired
	ItemsRepository iRepository;
	
	@Override
	public Users findByUserId(Users usersVO) {
		// 입력 받은 아이디 = UserId인 데이터의 아이디를 변수 findUser에 저장
		Users userVO = userRepository.findByUserId(usersVO.getUserId());
		
		return userVO;
	}
	
	@Override
	public Boolean loginCheck(Users userVO) {
		Users findUser = userRepository.findByUserId(userVO.getUserId());
		
		// 존재하는 ID인지 확인
		if(findUser == null) {
			return false;
		}
		else {
			// 입력한 PW가 일치한지 확인
			if(findUser.getUserPw().equals(userVO.getUserPw())) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	public List<OurCategory> OcFindAllByUsersVO(Users userVO) {
		return ocRepository.findAllByUsersVO(userVO);
	}

	@Override
	public List<Broadcasting> BcFindAllByUsersVO(Users userVO) {
		return bcRepository.findAllByUsersVO(userVO);
	}

	@Override
	public Broadcasting BcFindByBcSeq(Long bcSeq) {
		return bcRepository.findByBcSeq(bcSeq);
	}

	@Override
	public List<ViewerReaction> VcFindAllByBroadcastingVO(Broadcasting bcVO) {
		
		return vrRepository.findAllByBroadcastingVO(bcVO);
	}

	@Override
	public Map<Object, Object> getSalesInfo(Broadcasting bc) {
		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		
		/*1. 누적 판매 수량*/
		int cnt = vrRepository.getSalesSum(bc);	// 방송 진행 중 결제 수
		
		/*2. 예상 매출액*/
		// 방송에서 판매중인 제품들의 평균 가격
		double priceAvg = vrRepository.getPriceAvg(bc.getOurCategoryVO());
		
		// 정수 형태로 변환
		int salesPred = (int)(cnt * priceAvg * 1.3f);
		// 원 단위 : 콤마 추가
		String format = DecimalFormat.getInstance().format(salesPred);
		
		map.put("bcSeq", bc.getBcSeq());
		map.put("bcTitle", bc.getBcTitle());
		/*3. 남녀 시청자 수*/
		map.put("bcMale", bc.getBcMale());
		map.put("bcFemale", bc.getBcFemale());
		map.put("salesCnt", cnt);
		map.put("salesPred", format);	// String 형태
		
		// 아이템(제품)별 {카테고리:별점 평균} 리스트 가져오기
		List<Object> itemMapList = getStarsAvgGroupBy(bc);
		map.put("Items", itemMapList);
		
		return map;
	}

	@Override
	public List<Object> getStarsAvgGroupBy(Broadcasting bc) {
	
		// 방송에 따른 제품들 가져오기
		List<Items> itemsList = iRepository.findAllByOurCategoryVO(bc.getOurCategoryVO());
		
//		Map<Object, Object> map = new LinkedHashMap<Object, Object>();
		Map<String, Double> starMap = new LinkedHashMap<String, Double>();
		
		List<Object> itemMapList = new ArrayList<Object>();
		
		// 아이템마다 리뷰 카테고리별 별점 평균 가져오기
		for(Items item : itemsList) {	
			List<StarDto> list = rRepository.getStarAvg(item);
			Map<Object, Object> itemMap = new LinkedHashMap<Object, Object>();
			
			for(StarDto dto : list) {
				starMap.put(dto.getCategory(), dto.getAvg());
			}
			itemMap.put("item_seq", item.getItemSeq());
			itemMap.put("item_name", item.getItemName());
			itemMap.put("starAvg", starMap);
			itemMapList.add(itemMap);
		}
		
//		map.put("Items", itemMapList);
		return itemMapList;
	}
}
