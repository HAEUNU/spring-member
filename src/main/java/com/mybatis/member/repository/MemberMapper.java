package com.mybatis.member.repository;

import com.mybatis.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 테스트 클래스 자동 생성 : ctrl + shift + T > Create new Test.. 선택
// test 패키지 안, 아래 인터페이스와 같은 패키지 구조에 Test 클래스 자동 생성
// mapper인터페이스는 mapper xml의 sql태그를 호출하기 위한 인터페이스
@Mapper   // Mybatis 어노테이션으로, mapper 임을 선언
public interface MemberMapper {
    // 회원 1명 저장 : 외부에서 Member 객체에 저장할 정보를 담아 전달해 줄 것임.
    public void save(Member member);
    // 회원 1명 조회 : memberId 값 주고 조회, 레코드는 1개아니면 0개 -> 리턴타입 Member
    public Member findById(Integer id);
    // 회원 1명 username으로 조회
    public Member findByUsername(String username);
    // 전체 회원 조회 : 여러 레코드가 올수 있다 -> 리턴타입 Member를 List로 감싸기
    public List<Member> findAll();
    // 회원 정보 수정
    public void update(Member member);
    // 회원 정보 삭제
    public void delete(Integer id);
}
