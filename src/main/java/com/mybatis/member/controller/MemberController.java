package com.mybatis.member.controller;

import com.mybatis.member.domain.Member;
import com.mybatis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    // MemberService 객체 주입 받기
    private final MemberService memberService;

    // 회원 목록
    @GetMapping
    public String memberList(Model model) {
        log.info("Controller GET /members 요청!");
        // 회원 전체 목록을 DB에서 가져와 화면으로 전달
        // Controller: 서비스에게 회원 전체목록 가져와서 나한테 리턴해줘~ -> 결과를 View에 전달 (model)
        List<Member> memberList = memberService.findMembers();
        log.info("Controller GET /member - memberList : {}", memberList);
        model.addAttribute("list", memberList); // View에 결과 전달

        return "member/memberList";
    }


    // 회원 등록 폼 : 폼 html에 커맨드 오브젝트로 사용할 빈 Member 객체 전달
    @GetMapping("/new")
    public String newForm(@ModelAttribute("member") Member member) {
        log.info("Controller GET /members/new 요청!!");
        return "member/newForm";
    }
    // 회원 등록 처리
    @PostMapping("/new")
    public String newPro(Member member, RedirectAttributes rttr) {
        log.info("Controller POST /members/new member : {}", member);
        // 회원가입 처리 : Service -> DB에 저장하고 memberId(PK)값 돌려줘~
        int memberId = memberService.register(member);
        rttr.addAttribute("id", memberId); // 아래 redirect하는 주소에 {id}값 체워줌.
        rttr.addFlashAttribute("status", true); // 파라미터로 전송. 가입성공 메세지를 띄우기 위한 데이터 전송
        return "redirect:/members/{id}";  // 회원 상세페이지로 이동(연습용 페이지 흐름)
    }

    // 회원 상세 페이지 : 회원 1명 조회 /members/{id}?status=true
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) { // 주소에 있는 id값 꺼내기
        log.info("Controller GET /members/:id - id : {}", id);
        // 회원 1명에 대한 정보를 html 에 전달해줘야 함.
        // -> 회원 1명에 대한 정보를 DB에서 Member 객체로 가져와 여기서 model로 전달
        Member findMember = memberService.findById(id);
        log.info("Controller GET /members/:id - findMember : {}", findMember);
        // DB에서 조회해온 회원 1명 정보 View에 전달
        model.addAttribute("member", findMember);
        return "member/member"; // 상세 페이지 html
    }

    // 수정 폼 페이지 요청
    @GetMapping("/{id}/modify")
    public String modifyForm(@PathVariable("id") Integer id, Model model) {
        log.info("Controller GET /members/:id/modify - id : {}", id);
        // 로직처리 : 회원 정보를 DB에서 가져와 화면에 뿌려야함. -> 회원 id필요, -> 회원정보를 model 뷰에 전달
        Member findMember = memberService.findById(id);
        model.addAttribute("member", findMember);
        return "member/modifyForm"; // 수정 폼페이지 보여주기
    }
    // 수정 처리 페이지 요청
    @PostMapping("/{id}/modify")
    public String modifyPro(@PathVariable("id") Integer id, @ModelAttribute Member member) {
        log.info("Controller POST /:id/modify - id : {}", id);
        log.info("Controller POST /:id/modify - member : {}", member);
        // 로직처리 필요 : 비번확인, 맞으면 수정, 틀리면 되돌아가기
        boolean result = memberService.update(id, member);
        if(result) { // 비번 맞고 수정됨 -> 상세페이지로 이동
            // TODO delete처럼 rttr로 데이터보내 사용자에게 처리 결과 보여주도록 추가해보세요.
            return "redirect:/members/{id}"; // @PathVariable이 {id} 체워줌
        }else { // 비번 틀림 -> 수정 폼으로 되돌아가기
            // TODO delete처럼 rttr로 데이터보내 사용자에게 처리 결과 보여주도록 추가해보세요.
            return "member/modifyForm"; // @ModelAttribute Member member -> 화면에 정보 보이게 처리
        }
    }
    // 회원 삭제(탈퇴) 폼 요청
    @GetMapping("/{id}/delete")
    public String deleteForm() {
        return "member/deleteForm";
    }
    // 회원 삭제 처리 요청
    @PostMapping("/{id}/delete")
    public String deletePro(@PathVariable("id") Integer id, String pw, RedirectAttributes rttr) {
        log.info("Controller POST /delete - id : {}", id);
        log.info("Controller POST /delete - pw : {}", pw);
        // 로직처리 : 비번 확인해서 맞으면 삭제, 틀리면 삭제X, 다시 폼으로 돌아가기
        boolean result = memberService.remove(id, pw);
        log.info("Controller POST /delete - result : {}", result);
        if(result) {
            rttr.addFlashAttribute("deleteSuccess", true);
            return "redirect:/";  // 홈으로 리다이렉트
        }else {
            rttr.addFlashAttribute("deleteFail", true);
            return "redirect:/members/{id}/delete"; // 탈퇴 폼으로 리다이렉트
        }
    }



}
