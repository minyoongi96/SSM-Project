package com.mijung.SSM.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeechKeywordCateDto {
	private int speech_time;
	private String speech_keyword;
	private String speech_category;
}
