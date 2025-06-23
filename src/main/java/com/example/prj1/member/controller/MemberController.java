package com.example.prj1.member.controller;

import com.example.prj1.dto.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    @GetMapping("signup")
    public String signupFrom() {
        return "member/signup";
    }

    @PostMapping("signup")
    public String signup(MemberForm data, RedirectAttributes rttr) {
        //service
        System.out.println(data);

        rttr.addFlashAttribute("alert", Map.of("code", "", "message", "회원가입이 완료되었습니다."));

        return "redirect:/board/list";

    }

}
