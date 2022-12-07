package com.mijung.SSM.entity;

import java.sql.Date;

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
public class Comments {
	@Id
	@Column(name = "comment_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentSeq;
	
	@Column(name = "comment_content", nullable = false, length = 500)
	private String commentContent;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users usersVO;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private Board boardVO;
}
