package com.mybatis.member.service;

import com.mybatis.member.domain.Member;
import com.mybatis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
// 컨트롤러가 로직 처리하라고, 이 클래스의 메서드들 호출할 예정
@Service  // 비지니스영역의 서비스기능을(로직처리) 하는 객체로, 스프링빈으로 등록 시킴
@RequiredArgsConstructor  //final, @NonNull 붙은 변수를 매개변수로 갖는 생성자 자동생성
@Slf4j
public class MemberServiceImpl implements MemberService {

    // DB처리를 위한 Repository의 메서드들을 호출하기 위해, Repository 주입받기
    private final MemberRepository memberRepository;

    // 회원 가입 서비스
    @Override
    public Integer register(Member member) {
        log.info("Service register 메서드 요청 - member : {}", member);
        // 새 회원 정보 줄테니 DB가서 저장해~ 그리고 memberId값 리턴해줘~
        int memberId = memberRepository.save(member);
        // 저장 후 돌려받은 memberId값을 Controller로 리턴
        return memberId;
    }
    // 회원 정보 수정 서비스
    @Override
    public boolean update(Integer id, Member updateMember) {
        // 비번먼저 확인하고 맞으면 수정, 틀리면 수정X --> 비번 확인 여부를 boolean 리턴
        boolean result = false; // 비번 확인 최종 결과 담을 변수
        // * 비번 확인
        // DB에서 비번 가져오기
        Member findMember = memberRepository.findMemberById(id);
        String dbpw = findMember.getPassword();
        // updateMember의 사용자가 입력한 비번과 비교
        if(updateMember.getPassword().equals(dbpw)){
            // * 결과에 따른 수정 처리
            // 일치하면 수정
            updateMember.setMemberId(id); // 따로 넘어온 ID를 updateMember에 저장해서 한방에 넘기기
            memberRepository.updateMember(updateMember); // 회원 수정정보 줄테니 DB가서 수정해~
            result = true;
        }
        //      불일치하면 수정 X
        // * 비번 확인 결과 boolean으로 리턴
        return result;
    }
    // 회원 탈퇴 서비스
    @Override
    public boolean remove(Integer id, String pw) {
        boolean result = false;
        // 비번확인 : DB에서 비번 가져오기
        Member findMember = memberRepository.findMemberById(id);
        String dbpw = findMember.getPassword();
        if(pw.equals(dbpw)){
            // 맞으면 삭제
            // id 줄테니 DB가서 회원 정보 삭제해~
            memberRepository.deleteMember(id);
            result = true;
        }
        // 틀리면 삭제X, result는 그대로 false

        // 비번 확인 결과 boolean으로 리턴
        return result;
    }
    // 회원 고유번호(id)로 조회 서비스
    @Override
    public Member findById(Integer id) {
        log.info("Service findById - id : {}", id);

        // id들고 DB가서 조회해 Member 객체에 담아 들고 옴(리턴)
        Member findMember = memberRepository.findMemberById(id);
        log.info("Service findById - findMember : {}", findMember);

        // 조회한 Member(findMember)를 Controller로 리턴(전달)해줌
        return findMember;
    }
    // 회원 로그인id(username)으로 조회 서비스
    @Override
    public Member findByUsername(String username) {
        return null;
    }

    // 회원 전체 목록 조회 서비스
    @Override
    public List<Member> findMembers() {
        log.info("Service findMembers 요청!");
        // 서비스 : 회원 전체 목록 가져와 Controller한테 리턴해주기
        //         -> DB 접속해서 가져오기 -> Repository 에게 '목록가져와 리턴해줘~'
        List<Member> findMemberList = memberRepository.findAll();
        log.info("Service findMembers - findMemberList : {}", findMemberList);

        return findMemberList;
    }
    // 로그인 처리
    @Override
    public Member login(Member member) {
        // DB에서 username으로 회원 정보 가져오기
        Member findMember = memberRepository.findMemberByUsername(member.getUsername());
        if(findMember != null) { // username이 DB에 있고
            if(member.getPassword().equals(findMember.getPassword())){ // 비번이 맞으면 findMember 리턴
                return findMember;
            }
        }
        return null; // 조건이 하나라도 안맞으면 null 리턴
    }

}
