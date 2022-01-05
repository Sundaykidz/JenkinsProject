package com.koreait.jenkinsproject.service;

import java.util.Map;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.jenkinsproject.domain.Board;
import com.koreait.jenkinsproject.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardRepository repository;
	
	public BoardServiceImpl(SqlSessionTemplate sqlSession) {
		repository = sqlSession.getMapper(BoardRepository.class);
	}
	
	
	@Override
	public Map<String, Object> addBoard(MultipartHttpServletRequest multipartRequest) {

		String writer = multipartRequest.getParameter("writer");
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		Optional<String> opt = Optional.ofNullable(multipartRequest.getHeader("X-Forwarded-for"));
		String ip = opt.orElse(multipartRequest.getRemoteAddr());
		
		Board board = Board.builder()
				.writer(writer)
				.title(title)
				.content(content)
				.ip(ip)
				.build(); // .build() 끝을 의미함
		
		System.out.println("DB 수행전 = " + board);
		int result = repository.insertBoard(board);
		System.out.println("DB 수행후 = " + board);	
		return null;
	}

}
