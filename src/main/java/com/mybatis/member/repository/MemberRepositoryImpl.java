package com.mybatis.member.repository;

import com.mybatis.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

// alt + enter : 에러발생하는 코드에 커서를 두고 단축키 누르면, 문제 해결방법 intelliJ에서 제시해줌.
@Repository  // 저장소(DB) 관련된 클래스로 스프링빈으로 등록하는 어노테이션
@RequiredArgsConstructor // final, @NonNull 변수를 매개변수로 갖는 생성자 자동 생성 어노테이션
@Slf4j // 로그 찍기위해 추가
public class MemberRepositoryImpl implements MemberRepository{

    // @Repository어노테이션으로 스프링이 이 클래스를 객체 생성할때,
    // MemberMapper인터페이스(구현객체는 내부에서 자동처리)를 자동으로 주입해줌.
    private final MemberMapper memberMapper; // 생생자 매개변수로 포함

    @Override
    public Integer save(Member member) { // 외부에서 저장할 데이터를 Member 객체에 담아 줄것임.
        log.info("memberRepository - before save Member : {}", member);
        // mapper의 save() 인터페이스 호출 (MVC흐름에 맞게)
        memberMapper.save(member); // sql insert문 날려라~
        log.info("memberRepository - after save Member : {}", member);

        // 저장된 이후 auto_increment로 체워진 member 객체의 memberId 값을 리턴
        int memberId = member.getMemberId();
        return memberId;
    }

    @Override
    public Member findMemberById(Integer id) {
        log.info("Repository findMemberById - id : {}", id);
        Member findMember = memberMapper.findById(id);
        log.info("Repository findMemberById - findMember : {}", findMember);
        return findMember;
    }

    @Override
    public Member findMemberByUsername(String username) {
        Member findMember = memberMapper.findByUsername(username);
        return findMember;
    }

    @Override
    public List<Member> findAll() {
        // Repository : DB에 가서 회원 전체 정보가져와 서비스에게 리턴
        List<Member> findMemberList = memberMapper.findAll();
        return findMemberList;
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.update(member); // update SQL 실행해~
    }

    @Override
    public void deleteMember(Integer id) {
        memberMapper.delete(id);
    }


}
