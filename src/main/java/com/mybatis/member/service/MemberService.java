package com.mybatis.member.service;

import com.mybatis.member.domain.Member;

import java.util.List;

public interface MemberService {
    // 회원 가입
    Integer register(Member member);
    // 회원 정보 수정
    boolean update(Integer id, Member updateMember);
    // 회원 탈퇴
    boolean remove(Integer id, String pw);
    // 회원 1명 조회 : id로 조회, 유저네임(로그인아이디)으로 조회
    Member findById(Integer id);
    Member findByUsername(String username);
    // 회원 목록 조회
    List<Member> findMembers();
    // 로그인 처리
    Member login(Member member);
}
