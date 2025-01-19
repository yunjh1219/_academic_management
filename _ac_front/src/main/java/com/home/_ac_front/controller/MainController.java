package com.home._ac_front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MainController {


    // 루트 경로 ("/")에 접근 시 로그인 페이지로 리다이렉트
    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("/login");  // 로그인 페이지로 리다이렉트
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
    @GetMapping("/stu-total") //- 학생 포탈
    public String stuTotalPage() {return "pages/student/main/stu-total";}
    @GetMapping("/prof-total") //- 교수 포탈
    public String profTotalPage() {return "pages/professor/main/prof-total";}


    // 학생
    //============================================================================================
    @GetMapping("/stu-total-main")
    public String stuTotalMainPage() {return "pages/student/main/stu-total-main";}

    @GetMapping("/notice")
    public String notice() {return "pages/student/notice";}

    //수강
    @GetMapping("/cour-lnq")
    public String courlnq() {return "pages/student/courseenrollment/course-enrollment-lnquiry";}

    @GetMapping("/cour-ent")
    public String courent() {return "pages/student/courseenrollment/course-timetable-management";}

    @GetMapping("/cour-ion")
    public String courion() {return "pages/student/courseenrollment/course-registration";}


    //출석
    @GetMapping("/atten-iry")
    public String attiry() {return "pages/student/attendance/attendance-inquiry";}

    //과제
    @GetMapping("/ass-iry")
    public String assent() {return "pages/student/assignment/assignment-inquiry";}

    //성적
    //금학기 성적 조회
    @GetMapping("/gra-cur-iry")
    public String gracur() {return "pages/student/grade/current-semester-grade-inquiry";}

    //기이수 성적 조회
    @GetMapping("/gra-pre-iry")
    public String grapre() {return "pages/student/grade/previous-semester-grade-inquiry";}

    //등록
    //등록금 납부 확인
    @GetMapping("/regi-loaa")
    public String regiloaa() {return "pages/student/registration/tuition-payment-confirmation";}

    //등록금 고지서 확인
    @GetMapping("/regi-ra")
    public String regira() {return "pages/student/registration/tuition-bill-confirmation";}

    //복학신청
    @GetMapping("/regi-tbc")
    public String regitbc() {return "pages/student/registration/readmission-application";}

    //휴학신청
    @GetMapping("/regi-tpc")
    public String regitpc() {return "pages/student/registration/leave-of-absence-application";}

    //장학
    @GetMapping("/schol-sdc")
    public String scholsdc() {return "pages/student/scholarship/scholarship-details-confirmation";}
    //============================================================================================

    //교수




}
