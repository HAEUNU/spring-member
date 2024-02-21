package com.mybatis.member.repository;

import com.mybatis.member.domain.Member;

import java.util.List;

// 저장소 기능 처리
public interface MemberRepository {
    // 회원 가입
    public Integer save(Member member);
    // 회원 id로 조회
    public Member findMemberById(Integer id);
    // 회원 username으로 조회
    public Member findMemberByUsername(String username);
    // 회원 전제 조회
    public List<Member> findAll();
    // 회원 정보 수정
    public void updateMember(Member member);
    // 회원 정보 삭제
    public void deleteMember(Integer id);
}
