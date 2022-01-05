package com.koreait.jenkinsproject.service;

import java.util.Map;

import com.koreait.jenkinsproject.domain.Member;

public interface MemberService {
	public Map<String, Object> findAllMember(Integer page);
	public Map<String, Object> findMember(Long memberNo);
	public Map<String, Object> addMember(Member member);
	public Map<String, Object> modifyMember(Member member);
	public Map<String, Object> removeMember(Long memberNo);
}
