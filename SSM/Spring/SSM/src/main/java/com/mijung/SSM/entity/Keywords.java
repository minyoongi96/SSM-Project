package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Keywords {
	@Id
	@Column(name = "keyword_seq")
	private long keywordSeq;
	@Column(name = "keyword_name", length = 45)
	private String keywordName;
	@Column(name = "keyword_category", length = 45)
	private String keywordCategory;
	
}
