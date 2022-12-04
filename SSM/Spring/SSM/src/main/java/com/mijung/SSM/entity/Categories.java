package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Categories {
	@Id
	@Column(name = "cate_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cateSeq;
	
	@Column(name = "cate_name", nullable = false, length = 400)
	private String cateName;
}
