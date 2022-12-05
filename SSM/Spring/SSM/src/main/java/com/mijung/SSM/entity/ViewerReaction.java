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
@Table(name = "viewer_reaction")
@Data
public class ViewerReaction {
	@Id
	@Column(name ="vr_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vrSeq;
	
	@Column(name ="vr_times", nullable = false)
	private int vrTimes;
	
	@Column(name ="vr_viewers", nullable = false)
	private int vrViewers;
	
	@Column(name ="vr_sales", nullable = false)
	private int vrSales;
	
	@Column(name ="vr_lookings", nullable = false)
	private int vrLookings;
	
	@Column(name ="vr_baskets", nullable = false)
	private int vrBaskets;
	
	@Column(name ="vr_comments", nullable = false)
	private int vrComments;
	
	@Column(name ="vr_wishlists", nullable = false)
	private int vrWishlists;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bc_seq")
	private Broadcasting broadcastingVO;
	
}
