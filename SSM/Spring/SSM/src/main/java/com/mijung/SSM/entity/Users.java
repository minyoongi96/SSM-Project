package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Users {
	@Id           // pk
	@Column(name ="user_id",length = 30)
	private String userId;
	
	@Column(name ="user_pw", nullable = false,length = 30)
	private String userPw;
	
	@Column(name = "user_company", nullable = false,length = 30)
	private String userCompany;

	@Column(name = "user_type", nullable = false,length = 1)
	private String userType;
	
	/*
	OneToMany로 양방향 맺을 수는 있지만 단방향만으로도 비즈니스로직을 구현할 수 있다.
	 */
//	@OneToMany(mappedBy = "users")
//	private List<OurCategory> ourCategory;
	
}
