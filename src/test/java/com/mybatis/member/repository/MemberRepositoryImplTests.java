package com.mybatis.member.repository;

import com.mybatis.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;

import java.util.List;

@MybatisTest // Mybatis 관련 테스트용 어노테이션. @mapper 객체 생성
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MemberRepositoryImpl.class) // Repository : 테스트 클래스에서 사용할 수 있게 임포트
@Commit  // Test에서는 자동 Rollback이 된다. 확인을 위해 자동 Rollback 되지 않도록 Commit을 날리자
@Slf4j
class MemberRepositoryImplTests {

    @Autowired
    private MemberRepository memberRepository; // 임포트한 객체를 주입

    @Test
    public void testInject() {
        log.info("memberRepository : {}", memberRepository);
    }

    @Test
    public void insertTest() {
        // 저장 테스트
        // 테스트용 Member 객체 생성 + 저장할 회원 정보 추가
        Member member = new Member();
        member.setUsername("user04");
        member.setPassword("1111");
        member.setName("user4");
        member.setEmail("user4@test.com");

        memberRepository.save(member);
    }

    @Test
    public void findByIdTest() {
        Member findMember = memberRepository.findMemberById(1);
        log.info("findMember : {}", findMember);
    }

    @Test
    public void findAllTest() {
        List<Member> all = memberRepository.findAll();
        log.info("all : {}", all);
    }

    @Test
    public void updateTest() {
        // 업데이트 테스트용 Member객체 생성
        Member member = new Member();
        member.setMemberId(1); // 기존에 DB에 존재하는 member_id 값 <-- 얘를 수정
        member.setName("hahaha");
        member.setEmail("hahaha@test.com");

        memberRepository.updateMember(member); // 수정처리해~
    }

    @Test
    public void deleteTest() {
        memberRepository.deleteMember(2);
    }



}