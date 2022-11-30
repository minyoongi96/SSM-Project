package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Categories {
	@Id
	@Column(name = "cate_seq")
	private long cateSeq;
	@Column(name = "cate_name", length = 400)
	private String cateName;
}
