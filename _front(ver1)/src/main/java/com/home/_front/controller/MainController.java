package com.home._front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {return "pages/login/LoginPage";}


    @GetMapping("/stu-main")
    public String stumain() {return "pages/main/student-main";}


    //관리자
    //============================================================================================

    //포털 - 메인
    @GetMapping("/admin-potal")
    public String admin() {return "pages/admin/admin-po";}

    //사용자관리
    @GetMapping("/adminS1") //학생성적조회
    public String adminS1() {return "pages/admin/user-management/student-grade-inquiry";}

    @GetMapping("/adminS2") //학생관리
    public String adminS2() {return "pages/admin/user-management/student-management";}

    @GetMapping("/adminS3") //교수관리
    public String adminS3() {return "pages/admin/user-management/professor-management";}

    //행정관리
    @GetMapping("/adminP1") //학사일정
    public String acad() {return "pages/admin/administrative-management/academic-schedule";}

    @GetMapping("/adminP2") //공지사항
    public String acad2() {return "pages/admin/administrative-management/notice";}

    @GetMapping("/adminP3") //장학금 관리
    public String acad3() {return "pages/admin/administrative-management/scholarship-management";}

    @GetMapping("adminP4")  //등록금 납부관리
    public String acad4() {return "pages/admin/administrative-management/tuition-payment-management";}

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

}
