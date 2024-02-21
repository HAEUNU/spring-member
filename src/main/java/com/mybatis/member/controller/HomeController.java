package com.mybatis.member.controller;

import com.mybatis.member.domain.Member;
import com.mybatis.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/") // 메인
    public String home() {
        log.info("home 요청");
        return "home";
    }

    // 로그인 폼 요청
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") Member member) {
        return "login";
    }
    // 로그인 처리 요청
    @PostMapping("/login")
    public String loginPro(Member member, RedirectAttributes rttr, HttpSession session) {
        log.info("Controller POST /login - member : {}", member);
        // 로직처리 : username과 password가 DB에 저장된것과 일치하는지 확인
        Member resultMember = memberService.login(member);
        // 불일치 : login 폼페이지로 다시 되돌리기 : resultMember가 null 이다.
        if(resultMember == null) {
            rttr.addFlashAttribute("loginFail", true);
            return "redirect:/login";
        }
        // 일치 : session에 로그인 상태 저장 : resultMember에 데이터 들어있을것
        // 로그인 했다라는 상태 유지를 위한 데이터를 session에 객체 저장 (세션컨테이너에 속성 추가)
        session.setAttribute("loginMember", resultMember);
        return "redirect:/"; // 로그인 성공하면 홈으로 리다이렉트
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        //session.removeAttribute("loginMember"); // loginMember라는 이름의 데이터만 삭제
        session.invalidate(); // 세션에 저장된 모든 데이터 삭제
        return "redirect:/";
    }


}
