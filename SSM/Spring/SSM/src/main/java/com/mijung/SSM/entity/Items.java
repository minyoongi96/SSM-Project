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
public class Items {
	@Id
	@Column(name = "item_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itemSeq;
	
	@Column(name = "item_name", nullable = false, length = 45)
	private String itemName;
	
	@Column(name = "item_price", nullable = false)
	private int itemPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "our_seq")
	private OurCategory ourCategoryVO;
}
