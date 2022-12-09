package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mijung.SSM.entity.Items;
import com.mijung.SSM.entity.OurCategory;

public interface ItemsRepository extends JpaRepository<Items, Long>{
	List<Items> findAllByOurCategoryVO(OurCategory ocVO);

	Items findByItemSeq(Long itemSeq);
}
