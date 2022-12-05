package com.mijung.SSM.Dto;

import com.mijung.SSM.entity.Items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StarDto {
	String category;	// 리뷰 카테고리
	Double avg;			// 평균 별점
}


