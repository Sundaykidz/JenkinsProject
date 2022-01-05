package com.koreait.jenkinsproject.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface BoardService {

	public Map<String, Object> addBoard(MultipartHttpServletRequest multipartRequest);

	
}
