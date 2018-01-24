package com.ssgl.controller;

import com.ssgl.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 14:16
 */
@Controller
@RequestMapping("/faculty/")
public class FacultyController {

    @Autowired
    public FacultyService facultyService;

    @ResponseBody
    @RequestMapping(value = "selectAllFaculties",produces = "text/json;charset=utf-8")
    public String selectAllFaculties(){
        try {
            return facultyService.selectAllFaculties();
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    @ResponseBody
    @RequestMapping(value = "selectAllFacultiesByFacultyId",produces = "text/json;charset=utf-8")
    public String selectAllFacultiesByFacultyId(String facultyid){
        try {
            return facultyService.selectAllFacultiesByFacultyId(facultyid);
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

}
