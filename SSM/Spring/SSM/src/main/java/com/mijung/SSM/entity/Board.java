package com.mijung.SSM.entity;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table
@Data
public class Board {
	@Id
	@Column(name = "board_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long boardSeq;
	
	@Column(name = "board_title", nullable = false, length = 500)
	private String boardTitle;
	
	@Temporal(TemporalType.TIMESTAMP)	// 날짜 타입일 때 사용
	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", name = "board_date", nullable = false)
	private Date boardDate;
	
	@Column(name = "board_content", nullable = false, length=500)
	private String boardContents;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users usersVO;
}
