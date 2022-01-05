package com.koreait.jenkinsproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.koreait.jenkinsproject.domain.Member;
import com.koreait.jenkinsproject.repository.MemberRepository;
import com.koreait.jenkinsproject.util.PageUtils;

public class MemberServiceImpl implements MemberService {

	private MemberRepository repository;
	
	public MemberServiceImpl(SqlSessionTemplate sqlSession) {
		repository = sqlSession.getMapper(MemberRepository.class);
	}
	
	@Override
	public Map<String, Object> findAllMember(Integer page) {
		int totalRecord = repository.selectMemberCount();	// 페이지 계산
		PageUtils pageUtils = new PageUtils();				// 페이지 계산
		pageUtils.setPageEntity(totalRecord, page); 		// 페이지 계산
		
		// pageUtils를 하나씩 꺼내서 쓰면 확장성이 좋다
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("beginRecord", pageUtils.getBeginRecord());
		m.put("endRecord", pageUtils.getEndRecord());

		List<Member> list = repository.selectMemberList(m);	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageUtils", pageUtils);
		map.put("list", list);
		return map;
	}

	@Override
	public Map<String, Object> findMember(Long memberNo) {
		Member member =  repository.selectMemberByNo(memberNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);
		return map;
	}

	@Override
	public Map<String, Object> addMember(Member member) {
		int result = repository.insertMember(member);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("memberNo", member.getMemberNo());
		return map;
	}

	@Override
	public Map<String, Object> modifyMember(Member member) {
		int result = repository.updateMember(member);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@Override
	public Map<String, Object> removeMember(Long memberNo) {
		int result = repository.deleteMember(memberNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

}
