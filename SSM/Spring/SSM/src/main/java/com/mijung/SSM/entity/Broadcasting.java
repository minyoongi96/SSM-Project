package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Broadcasting {
	
	@Id
	@Column(name = "bc_seq")
	private long bdSeq;
	
	@Column(name = "user_id", length = 30)
	private String userId;
	@Column(name = "our_seq")
	private long ourSeq;
	@Column(name = "bc_title", length = 400)
	private String bcTitle;
}
