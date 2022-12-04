package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "speech_analysis")
@Data
public class SpeechAnalysis {
	@Id
	@Column(name ="speech_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long speechSeq;
	
	@Column(name ="speech_keyword", nullable = false,length = 45)	
	private String speechKeyword;
	
	@Column(name ="speech_time", nullable = false)
	private int speechTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bc_seq")
	private Broadcasting broadcastingVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_seq")
	private Keywords keywordsVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vr_seq")
	private ViewerReaction viewerReactionVO;
}
