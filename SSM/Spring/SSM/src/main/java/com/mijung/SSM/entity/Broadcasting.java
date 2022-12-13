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
	
	// Primary key
	@Id
	@Column(name = "bc_seq")			// DB 테이블에서 사용되는 실제 컬럼
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// 자동 증가
	private long bcSeq;					 
	
	@Column(name = "bc_title", nullable = false, length = 400)	// 컬럼 속성
	private String bcTitle;				// Entity 클래스에서 사용할 속성(필드)명
	
	@Column(name = "bc_male", nullable = false)
	private int bcMale;
	
	@Column(name = "bc_female", nullable = false)
	private int bcFemale;
	
	@Column(name = "bc_url", nullable=false, length=1000)
	private String bcUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)		// 1:N 관계 매핑 (users 테이블과 매핑)
	@JoinColumn(name = "user_id")
	private Users usersVO;					// 객체 자체를 참조함 
 	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "our_seq", nullable = false)
	private OurCategory ourCategoryVO;

}
