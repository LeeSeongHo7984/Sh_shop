package kr.inhatc.spring.member.controller;

import kr.inhatc.spring.member.dto.MemberFormDto;
import kr.inhatc.spring.member.entity.Member;
import kr.inhatc.spring.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/member")
@Log4j2
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 로그인
    @GetMapping("/login")
    public String login() {

        return "member/memberLogin";
    }


    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, // valid 를 쓰면 bindingResult 가 같이 붙음 
                             BindingResult bindingResult, Model model) { // 값들이 채워졌나 안채워졌나 확인

        // annotation 에서 에러가 있으면 다시 memberForm으로 돌아간다 -> 바이딩 에러 시 처리
        if(bindingResult.hasErrors()) {

            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "member/memberFrom";
        }

        return "redirect:/";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 패스워드가 잘못되었습니다.");

        return "member/memberLogin";
    }


}
