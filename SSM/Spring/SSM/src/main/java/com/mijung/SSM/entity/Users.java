package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
public class Users {
	@Id           // pk
	@Column(name ="user_id",length = 30)
	private String userId;
	
	@Column(name ="user_pw",length = 30)
	private String userPw;
	
	@Column(name = "user_company",length = 30)
	private String userCompany;

	@Column(name = "user_type",length = 1)
	private String userType;
	
}
