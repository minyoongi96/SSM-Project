package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Items {
	@Id
	@Column(name = "item_seq")
	private long itemSeq;
	@Column(name = "our_seq")
	private long ourSeq;
	@Column(name = "item_name", length = 45)
	private String itemName;
	@Column(name = "item_price")
	private int itemPrice;
}
