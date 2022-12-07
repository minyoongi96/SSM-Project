package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	List<Board> findAll();
	
	
}
