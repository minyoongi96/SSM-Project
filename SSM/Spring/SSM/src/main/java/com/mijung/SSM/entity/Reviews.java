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
@Table
@Data
public class Reviews {
	@Id
	@Column(name = "review_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewSeq;
	
	@Column(name = "review_category", nullable = false, length = 45)
	private String reviewCategory;
	
	@Column(name = "review_sentiment", nullable = false)
	private double reviewSentiment;
	
	@Column(name = "review_star", nullable = false)
	private int reviewStar;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_seq")
	private Items itemsVO;
}
