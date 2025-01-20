package com.example.campushub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {


    // 루트 경로 ("/")에 접근 시 로그인 페이지로 리다이렉트
    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("/prof-total");  // 로그인 페이지로 리다이렉트
    }

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String loginPage() {
        return "pages/login";  // login.html을 렌더링
    }

    //메인페이지
    @GetMapping("/stu-main") //- 학생 메인
    public String stuMainPage() {return "pages/student/main/stu-main";}
    @GetMapping("/prof-main") //- 교수 메인
    public String profMainPage() {return "pages/professor/main/prof-main";}

    //포탈페이지
    @GetMapping("/stu-total")
    public String stuTotalPage() {return "pages/student/main/stu-total";}
    @GetMapping("/prof-total")
    public String profTotalPage() {return "pages/professor/main/prof-total";}


}
