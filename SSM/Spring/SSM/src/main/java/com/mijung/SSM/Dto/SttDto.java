package com.mijung.SSM.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SttDto {
	private Long salesCnt;		// +3분 판매 누적
	private Long viewerCnt;		// +3분 시청자 누적
	private Long lookingCnt;	// +3분 상품 조회 누적
	private Long basketCnt;		// +3분 장바구니 누적
	private Long commentCnt;	// +3분 댓글수 누적
	private Long wishlistCnt;	// +3분 좋아요 수 누적
}
