package com.example.campushub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    //첫 페이지 로그인 화면
    @GetMapping("/")
    public String index() {return "pages/login";}

    //학생 메인 페이지
    @GetMapping("/mainpage")
    public String mainpage() {return "pages/main/main";}

    //교수 메인 페이지
    @GetMapping("/propmain")
    public String propmain() {return "promain";}

    //관리자 메인 페이지
    @GetMapping("/adminmain")
    public String adminmain() {return "pages/main/adminmain";}

    //학생 바로가기
    @GetMapping("/student")
    public String student() {return "pages/student/default";}

    //관리자 바로가기
    @GetMapping("/administrator")
    public String administrator() {return "pages/administrator/default";}

    // 학생
    //============================================================================================
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

    // 관리자
    //============================================================================================

    //사용자 관리
    @GetMapping("/user-sgi") //학생 성적 조회
    public String usersgi() {return  "pages/administrator/user-management/student-grade-inquiry";}

    @GetMapping("/user-sm") //학생 관리
    public String usersm() {return  "pages/administrator/user-management/student-management";}
    //============================================================================================
}
