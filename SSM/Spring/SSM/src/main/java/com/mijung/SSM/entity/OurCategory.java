package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class OurCategory {
	@Id
	@Column(name = "our_seq")
	private long ourSeq;
	@Column(name = "cate_seq")
	private long cateSeq;
	
	@Column(name = "user_id", length = 30)
	private String userId;
	@Column(name = "user_company", length = 30)
	private String userCompany;
	
	
}
