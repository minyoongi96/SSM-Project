package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class SpeechAnalysis {
	@Id
	@Column(name ="speech_seq")
	private long speechSeq;
	@Column(name ="bc_seq")
	private long bcSeq;
	
	@Column(name ="speech_keyword",length = 45)	
	private String speechKeyword;
	
	@Column(name ="speech_time")
	private int speechTime;
	@Column(name ="keyword_seq")
	private long keywordSeq;
	
	@Column(name ="vr_seq")
	private long vrSeq;
	
}
