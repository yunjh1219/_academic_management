package com.home.academicmanagementdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/")
    public String courseRegInq() {return "pages/student/course-reg-inq";}

    @GetMapping("/course-reg-inq")
    public String notice() {return "pages/student/notice";}

    @GetMapping("/tab2")
    public String courseRegIntq() {return "pages/student/tab2";}

}
