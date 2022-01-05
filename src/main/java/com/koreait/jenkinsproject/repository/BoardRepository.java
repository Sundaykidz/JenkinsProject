package com.koreait.jenkinsproject.repository;

import org.springframework.stereotype.Repository;

import com.koreait.jenkinsproject.domain.Board;

@Repository
public interface BoardRepository {
	
	public int insertBoard(Board board);

}
