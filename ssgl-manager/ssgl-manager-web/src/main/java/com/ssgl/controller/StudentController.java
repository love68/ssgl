package com.ssgl.controller;
/*
 * 功能:学生相关操作
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/30 0030
 * Time: 23:53
 */

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Student;
import com.ssgl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @RequestMapping("managerStudent")
    public String managerStudent() {
        return "student";
    }

    @ResponseBody
    @RequestMapping(value = "selectStudentsPage", produces = "text/json;charset=utf-8")
    public String selectStudentsPage(Integer page, Integer rows, HttpServletRequest request) {
        try {
            Page<Student> page1 = studentService.selectStudentsPage(page, rows, request);
            Map<String, Object> map = new HashMap<>();
            if (null != page1) {
                map.put("total", page1.getTotalRecord());
                map.put("rows", page1.getList());
                return JSONObject.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "selectStudentInfo")
    public ModelAndView selectStudentInfo(String id) {
        try {
            Student student = studentService.selectStudentInfo(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("StudentInfo");
            modelAndView.addObject("student",student);
            return modelAndView;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

