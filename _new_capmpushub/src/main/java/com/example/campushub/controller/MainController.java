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

    //mypage
    @GetMapping("/mypage_info")
    public String myinfoPage() { return "pages/mypage/mypage_info"; }

    @GetMapping("/mypage_pw")
    public String mypwPage() { return "pages/mypage/mypage_pw"; }


    // 메인페이지
    @GetMapping("/stu_main")
    public String stuMainPage() { return "pages/main/stu_main"; }
    @GetMapping("/prof_main")
    public String profMainPage() { return "pages/main/prof_main"; }

    //통합정보
    @GetMapping("/stu_total")
    public String stuTotalPage() { return "pages/total/stu_total"; }
    @GetMapping("/admin_total")
    public String admin() {return "pages/total/admin_total"; }
    @GetMapping("/prof_total")
    public String profTotalPage() { return "pages/total/prof_total"; }

    //관리자
    @GetMapping("/admin_usermana_stu")
    public String AdminUsermanaStu(){return "pages/admin/user-management/student-management";}
    @GetMapping("/admin_usermana_prof")
    public String AdminUsermanaProf(){return "pages/admin/user-management/professor-management.html";}
    @GetMapping("/admin_usermana_dept")
    public String AdminUsermanaDept(){return "pages/admin/user-management/dept-management.html";}
    @GetMapping("/admin_usermana_stugrad")
    public String AdminUsermanaStugrad(){return "pages/admin/user-management/student-grade-inquiry.html";}


    //행정관리
    @GetMapping("/admin_admini_schedule") //학사일정
    public String acad1() {return "pages/admin/administrative-management/academic-schedule.html";}
    @GetMapping("/admin_admini_notice") //공지사항
    public String acad2() {return "pages/admin/administrative-management/notice.html";}
    @GetMapping("/admin_admini_scholarship") //장학금 관리
    public String acad3() {return "pages/admin/administrative-management/scholarship-management.html";}
    @GetMapping("/admin_admini_tuition")  //등록금 납부관리
    public String acad4() {return "pages/admin/administrative-management/tuition-payment-management.html";}


    // 학생
    //============================================================================================
    //수강
    @GetMapping("/cour-lnq")
    public String courlnq() {return "pages/student/courseenrollment/course-enrollment-lnquiry";}
    @GetMapping("/cour-ent")
    public String courent() {return "pages/student/courseenrollment/course-timetable-management";}
    @GetMapping("/cour-ion")
    public String courion() {return "pages/student/courseenrollment/course-registration";}

    @GetMapping("/atten-iry")   //출석
    public String attiry() {return "pages/student/attendance/attendance-inquiry";}
    @GetMapping("/ass-iry")  //과제
    public String assent() {return "pages/student/assignment/assignment-inquiry";}

    //성적

    @GetMapping("/gra-cur-iry")    //금학기 성적 조회
    public String gracur() {return "pages/student/grade/current-semester-grade-inquiry";}
    @GetMapping("/gra-pre-iry")   //기이수 성적 조회
    public String grapre() {return "pages/student/grade/previous-semester-grade-inquiry";}

    //등록
    @GetMapping("/regi-loaa")    //등록금 납부 확인
    public String regiloaa() {return "pages/student/registration/tuition-payment-confirmation";}
    @GetMapping("/regi-ra")    //등록금 고지서 확인
    public String regira() {return "pages/student/registration/tuition-bill-confirmation";}
    @GetMapping("/regi-tbc")    //복학신청
    public String regitbc() {return "pages/student/registration/readmission-application";}
    @GetMapping("/regi-tpc")    //휴학신청
    public String regitpc() {return "pages/student/registration/leave-of-absence-application";}
    @GetMapping("/schol-sdc") //장학
    public String scholsdc() {return "pages/student/scholarship/scholarship-details-confirmation";}
    //============================================================================================

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

    //강의 시간표
    @GetMapping("/course-timetable")
    public String courseTimeTable() {return "pages/professor/course/prof-course-timetable";}

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
