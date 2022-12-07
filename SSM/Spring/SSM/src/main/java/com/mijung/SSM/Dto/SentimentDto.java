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
public class SentimentDto {
	private String category;	// 리뷰 카테고리
	private Double avg;			// 평균 별점
}


