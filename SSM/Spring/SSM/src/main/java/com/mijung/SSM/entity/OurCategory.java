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
@Table(name = "our_category")
@Data
public class OurCategory {
	@Id
	@Column(name = "our_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ourSeq;
	
	@Column(name = "user_company", nullable = false, length = 30)
	private String userCompany;
	
	/*
	@JoinColumn -> 외래키를 매핑할 때 사용
	name 속성에는 매핑할 외래키 이름을 지정 -> 그렇다면? 첨에 엔티티 작성할 때 외래키 변수는 지워버려야함
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Users usersVO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cate_seq")
	private Categories categoriesVO;
}
