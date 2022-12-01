package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Reviews {
	@Id
	@Column(name = "review_seq")
	private long reviewSeq;
	@Column(name = "item_seq")
	private long itemSeq;
	
	@Column(name = "review_category", length = 45)
	private String reviewCategory;
	
	@Column(name = "review_sentiment")
	private double reviewSentiment;
	
	@Column(name = "review_star")
	private int reviewStar;
	
}
