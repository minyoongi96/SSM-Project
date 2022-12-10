package com.mijung.SSM.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mijung.SSM.entity.Board;
import com.mijung.SSM.entity.Comments;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>{
	Comments findByBoardVO(Board boardVO);
	
}
