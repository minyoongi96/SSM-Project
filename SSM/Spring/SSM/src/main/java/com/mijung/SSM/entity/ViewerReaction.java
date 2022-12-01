package com.mijung.SSM.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class ViewerReaction {
	@Id
	@Column(name ="vr_seq")
	private long vrSeq;
	
	@Column(name ="bc_seq")
	private long bcSeq;
	
	@Column(name ="vr_times")
	private int vrTimes;
	
	@Column(name ="vr_viewrs")
	private int vrViewrs;
	
	@Column(name ="vr_sales")
	private int vrSales;
	
	@Column(name ="vr_lookings")
	private int vrLookings;
	
	@Column(name ="vr_baskets")
	private int vrBaskets;
	
	@Column(name ="vr_comments")
	private int vrComments;
	
	@Column(name ="vr_wishlists")
	private int vrWishlists;
	
}
