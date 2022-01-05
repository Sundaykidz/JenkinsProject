package com.koreait.jenkinsproject.repository;

import org.springframework.stereotype.Repository;

import com.koreait.jenkinsproject.domain.BoardAttach;

@Repository
public interface BoardAttachRepository {

	public int insertBoardAttac(BoardAttach boardAttach);
	
}
