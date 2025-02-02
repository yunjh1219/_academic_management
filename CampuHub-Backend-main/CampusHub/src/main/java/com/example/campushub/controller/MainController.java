package com.example.campushub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 루트 경로 ("/")에 접근 시 로그인 페이지로 리다이렉트
    @GetMapping("/")
    public String home() {
        return "redirect:/api/login";
    }
    // 로그인 페이지 보여주기
    @GetMapping("/api/login")
    public String loginPage() {
        return "pages/common/login";  // login.html을 렌더링
    }

    // 아이디(학번/교번) , 비밀번호 찾기
    @GetMapping("/findID")
    public String findAccountPage() { return "pages/common/findID"; }
    @GetMapping("/findPW")
    public String findPasswordPage() { return "pages/common/findPW"; }

    // 메인페이지
    @GetMapping("/stu_main")
    public String stuMainPage() { return "pages/main/stu_main"; }
    @GetMapping("/prof_main")
    public String profMainPage() { return "pages/main/prof_main"; }

    //통합정보
    @GetMapping("/stu_total")
    public String stuTotalPage() { return "pages/main/stu_total"; }
    @GetMapping("/admin_total")
    public String admin() {return "pages/main/admin_total";}
    @GetMapping("/prof_total")
    public String profTotalPage() { return "pages/main/prof_total"; }

    //mypage
    @GetMapping("/mypage_info")
    public String myinfoPage() { return "pages/mypage/mypage_info"; }

    @GetMapping("/mypage_pw")
    public String mypwPage() { return "pages/mypage/mypage_pw"; }

    // 학생
    //============================================================================================
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

    //관리자
    //============================================================================================



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



    //교수
    //============================================================================================

    //금일 출석관리
    @GetMapping("/atten-daily")
    public String attenDaily() {return "pages/professor/attendance/daily-attendance";}

    //출석 통계
    @GetMapping("/atten-tot")
    public String attenTot() {return "pages/professor/attendance/attendance-total";}

    //강의 수강 등록
    @GetMapping("/course-regist")
    public String courseRegist() {return "pages/professor/course/prof-course-registration";}




    //성적 기입
    @GetMapping("/enter-grade")
    public String enterGrade() {return "pages/professor/grade/enter-grade";}

    //성적 처리
    @GetMapping("/grade-processing")
    public String gradeProcessing() {return "pages/professor/grade/grade-processing";}

    //과제 등록
    @GetMapping("/assignment-registration")
    public String assignmentRegistration() {return "pages/professor/assignment/assignment-registration";}

    //과제 점수 관리
    @GetMapping("/assignment-total")
    public String assignmentTotal() {return "pages/professor/assignment/assignment-total";}

    //과제 확인
    @GetMapping("/assignment-check")
    public String assignmentcheck() {return "pages/professor/assignment/assignment-check";}

    //공지사항
    @GetMapping("/prof-notice")
    public String profNotice() {return "pages/professor/notice/notice";}



}
