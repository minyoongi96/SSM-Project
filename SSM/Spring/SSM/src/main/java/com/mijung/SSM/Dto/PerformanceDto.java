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
public class PerformanceDto {
	// 실적 DTO
	// 실적 -> 결제 수 / 장바구니 수
	private Long totalBaskets;
	private Long totalSales;
}
