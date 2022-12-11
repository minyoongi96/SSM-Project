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
public class Broadcasting {
	
	@Id
	@Column(name = "bc_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bcSeq;
	
	@Column(name = "bc_title", nullable = false, length = 400)
	private String bcTitle;
	
	@Column(name = "bc_male", nullable = false)
	private int bcMale;
	
	@Column(name = "bc_female", nullable = false)
	private int bcFemale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users usersVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "our_seq", nullable = false)
	private OurCategory ourCategoryVO;
	
	@Column(name = "bc_url", nullable=false, length=1000)
	   private String bcUrl;
}
