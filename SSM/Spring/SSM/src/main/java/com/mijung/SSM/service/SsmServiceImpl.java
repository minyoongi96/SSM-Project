package com.mijung.SSM.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mijung.SSM.Dto.PerformanceDto;
import com.mijung.SSM.Dto.SentimentDto;
import com.mijung.SSM.Dto.SpeechKeywordCateDto;
import com.mijung.SSM.Dto.StarDto;
import com.mijung.SSM.Dto.SttDto;
import com.mijung.SSM.Dto.TimeKeywordDto;
import com.mijung.SSM.entity.Board;
import com.mijung.SSM.entity.Broadcasting;
import com.mijung.SSM.entity.Comments;
import com.mijung.SSM.entity.Items;
import com.mijung.SSM.entity.OurCategory;
import com.mijung.SSM.entity.SpeechAnalysis;
import com.mijung.SSM.entity.Users;
import com.mijung.SSM.entity.ViewerReaction;
import com.mijung.SSM.repository.BoardRepository;
import com.mijung.SSM.repository.BroadcastingRepository;
import com.mijung.SSM.repository.CommentsRepository;
import com.mijung.SSM.repository.ItemsRepository;
import com.mijung.SSM.repository.KeywordsRepository;
import com.mijung.SSM.repository.OurCategoryRepository;
import com.mijung.SSM.repository.ReviewsRepository;
import com.mijung.SSM.repository.SpeechAnalysisRepository;
import com.mijung.SSM.repository.UsersRepository;
import com.mijung.SSM.repository.ViewerReactionRepository;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;

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
	
	@Autowired
	SpeechAnalysisRepository saRepository;
	
	@Autowired
	KeywordsRepository kRepository;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	CommentsRepository cmRepository;
	
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
		
		/* 1. 누적 판매 수량*/
		int cnt = vrRepository.getSalesSum(bc);	// 방송 진행 중 결제 수
		
		/* 2. 예상 매출액*/
		// 방송에서 판매중인 제품들의 평균 가격
		double priceAvg = vrRepository.getPriceAvg(bc.getOurCategoryVO());
		
		// 정수 형태로 변환
		int salesPred = (int)(cnt * priceAvg * 1.3f);
		// 원 단위 : 콤마 추가
		String format = DecimalFormat.getInstance().format(salesPred);
		
		map.put("bcSeq", bc.getBcSeq());
		map.put("bcTitle", bc.getBcTitle());
		/* 3. 남녀 시청자 수*/
		map.put("bcMale", bc.getBcMale());
		map.put("bcFemale", bc.getBcFemale());
		map.put("salesCnt", cnt);
		map.put("salesPred", format);	// String 형태
		
		/* 4. 아이템(제품)별 {카테고리: {별점 평균}, {감정점수 평균} 리스트 가져오기 */
		List<Object> itemMapList = getStarsAvgGroupBy(bc);
		map.put("Items", itemMapList);
		
		/* 5. 실적률 가져오기 */
		double porformance = (double)Math.round(getPerformRate(bc) * 100) / 100d;
		map.put("performance", porformance);
		
		/* 6. 1~60분 단위 시청자 반응 정보 가져오기 */
		Map<Integer, Object> reactionMap = getViewReactionChart(bc);
		map.put("ViewerReactions", reactionMap);
		
		/* 7. 등장한 키워드 별 등장시간과 +3분 시청자 반응 누적 가져오기 */
		List<Object> keywordCountList = getKeywordCount(bc);
		map.put("KeywordReactionCount", keywordCountList);
		
		/* 8. 각 키워드 카테고리별 기여도 가져오기*/
//		Map<String, Long> involveMap = getInvolvement(bc);
//		map.put("CategoryInvolvement", involveMap);
		return map;
	}

	@Override
	public List<Object> getStarsAvgGroupBy(Broadcasting bc) {
	
		/* 4. item리스트 -> [{item_seq, item_name, 카테고리별 별점 평균, 카테고리별 감정점수 평균}]*/
		// 방송에 따른 제품들 가져오기
		List<Items> itemsList = iRepository.findAllByOurCategoryVO(bc.getOurCategoryVO());
		
		
		List<Object> itemMapList = new ArrayList<Object>();
		
		// 아이템마다 리뷰 카테고리별 별점, 감정점수 평균 가져오기
		for(Items item : itemsList) {
			// 아이템별로 카테고리당 별점, 감정점수 들어있는 리스트 불러오기
			List<StarDto> starList = rRepository.getStarAvg(item);
			List<SentimentDto> sentimentList = rRepository.getSentimentAvg(item);
			
			// 별점, 감정점수 담을 맵
			Map<Object, Object> itemMap = new LinkedHashMap<Object, Object>();
			
			Map<String, Double> starMap = new LinkedHashMap<String, Double>();
			Map<String, Double> sentimentMap = new LinkedHashMap<String, Double>();
			
			for(StarDto dto : starList) {
				starMap.put(dto.getCategory(), dto.getAvg());
			}
			for(SentimentDto dto : sentimentList) {
				sentimentMap.put(dto.getCategory(), dto.getAvg());
			}
			
			itemMap.put("item_seq", item.getItemSeq());
			itemMap.put("item_name", item.getItemName());
			itemMap.put("star_avg", starMap);
			itemMap.put("sentiment_avg", sentimentMap);
			itemMapList.add(itemMap);
		}
		
		return itemMapList;
	}

	// 5. 실적률 계산
	@Override
	public Double getPerformRate(Broadcasting bc) {
		PerformanceDto dto = vrRepository.getPerformance(bc);
		Double result = dto.getTotalSales() / (double)dto.getTotalBaskets() * 100 * 1.4f;
		
		return result;
	}
	// 6. 1 ~ 60분 까지 사용자 반응 정보를 담은 Map을 리스트로 반환
	// 형태 : [1 : {시청자 반응}, 2: {시청자 반응}, ...]
	@Override
	public Map<Integer, Object> getViewReactionChart(Broadcasting bc) {
		// 방송에 따른 ViewerReaction 불러오기
		List<ViewerReaction> vrList = vrRepository.findAllByBroadcastingVO(bc);
		
		// 1 ~ 60분까지 사용자 반응 Map 데이터 담을 맵 키값은 분으로
		Map<Integer, Object> timeMap = new LinkedHashMap<Integer, Object>();
//		List<Object> result = new ArrayList<Object>();
		for(ViewerReaction vr : vrList) {
			
			Map<String, Integer> temp = new LinkedHashMap<String, Integer>();
			
			temp.put("viewer", vr.getVrViewers());
			temp.put("sale", vr.getVrSales());
			temp.put("looking", vr.getVrLookings());
			temp.put("basket", vr.getVrBaskets());
			temp.put("comment", vr.getVrComments());
			temp.put("wishlist", vr.getVrWishlists());
			
			timeMap.put((Integer)vr.getVrTimes(), temp);
//			result.add(timeMap);
		}
		
//		return result;
		return timeMap;
	}

	// 7. 등장 키워드, 키워드 카테고리, 등장 시간, +3분 누적 반응 모은 객체 쌍의 Map을 담은 list 반환
	@Override
	public List<Object> getKeywordCount(Broadcasting bc) {
		// 해당 방송에 나온 키워드 모음
		List<TimeKeywordDto> timeKeywordList = saRepository.findKeywordTime(bc);
		List<Object> list = new ArrayList<Object>();
		
		// 해당 방송에 나온 키워드,시간 모음
		for(TimeKeywordDto timeKeywordDto : timeKeywordList) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			String keyword_category = kRepository.findCategory(timeKeywordDto.getKeyword());
			// 시간만 뽑아서 repository에 전달
			int speech_time = timeKeywordDto.getSpeechTime();
			if(speech_time < 58) {				
				// 해당 시간의 +3분 시청자 반응 누적
				SttDto sttDto = vrRepository.findAllToSttDto(bc, speech_time);
				
				map.put("speech_keyword", timeKeywordDto.getKeyword());
				map.put("keyword_category", keyword_category);
				map.put("speech_time", speech_time);
				map.put("reaction_count", sttDto);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public List<Board> BoardfindAll() {
		List<Board> boardList = boardRepository.findAll();
		
		return boardList;
	}

	// boardVO와 연결된 댓글리스트 가져오기
	@Override
	public Comments CmFindByBoardVO(Board boardVO) {
		Comments comment = cmRepository.findByBoardVO(boardVO);
		
		return comment;
	}
	
	// get방식으로 boardList에서 boardSeq 가져오면 boardVO 생성
	@Override
	public Board BoardFindByBoardSeq(Long BoardSeq) {
		Board boardVO = boardRepository.findAllByBoardSeq(BoardSeq);
		return boardVO;
	}
	

	
	// 아이템 감성점수 데이터
	@Override
	public Map<String, Double> itemSentiment(Long itemSeq) {
		Items item = iRepository.findByItemSeq(itemSeq);
		List<SentimentDto> sentimentList = rRepository.getSentimentAvg(item);
		Map<String, Double> ItemSentimentMap = new HashMap<String, Double>();
		
		for(SentimentDto sentiment : sentimentList) {
			ItemSentimentMap.put(sentiment.getCategory(), sentiment.getAvg());
		}
		
		return ItemSentimentMap;
	}
	
	@Override
	public Map<String, Double> itemStar(Long itemSeq){
		Items item = iRepository.findByItemSeq(itemSeq);
		List<StarDto> starList = rRepository.getStarAvg(item);
		Map<String, Double> ItemStarMap = new HashMap<String, Double>();
		
		for(StarDto star : starList) {
			ItemStarMap.put(star.getCategory(), star.getAvg());
		}
		
		return ItemStarMap;
	}	

}
