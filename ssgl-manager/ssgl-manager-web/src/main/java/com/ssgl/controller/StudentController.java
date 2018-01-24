package com.ssgl.controller;
/*
 * 功能:学生相关操作
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/30 0030
 * Time: 23:53
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/")
public class StudentController {
    @RequestMapping("managerStudent")
    public String selectStudent(){
        return "student/student";
    }

    @RequestMapping("selectStudentsPage")
    public String selectStudentsPage(){
        return "";
    }



}
