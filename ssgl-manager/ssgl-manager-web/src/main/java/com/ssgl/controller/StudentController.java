package com.ssgl.controller;
/*
 * 功能:学生相关操作
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/30 0030
 * Time: 23:53
 */

import com.ssgl.bean.Result;
import com.ssgl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Controller
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @RequestMapping("managerStudent")
    public String managerStudent() {
        return "student/student";
    }

    @RequestMapping("selectStudentsPage")
    public String selectStudentsPage() {
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "addStudent")
    public Result addStudent(String sid,
                             String name,
                             Integer age,
                             Boolean sex,
                             Date graduateTime,
                             String homePhone,
                             String entranceTime,
                             Boolean isUndergraduate,
                             Boolean isGraduate,
                             String roomNumber,
                             String dormitoryNo,
                             String bedNo,
                             String province,
                             String city,
                             String county,
                             String phone,
                             String duty,
                             String faculty,
                             @RequestParam(value = "icon") MultipartFile icon) {

        try {
            return studentService.addStudent(sid,
                    name,
                    age,
                    sex,
                    graduateTime,
                    homePhone,
                    entranceTime,
                    isUndergraduate,
                    isGraduate,
                    roomNumber,
                    dormitoryNo,
                    bedNo,
                     province,
                     city,
                     county,
                    phone,
                    duty,
                    faculty,
                    icon);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error", "添加失败");
        }
    }
}

