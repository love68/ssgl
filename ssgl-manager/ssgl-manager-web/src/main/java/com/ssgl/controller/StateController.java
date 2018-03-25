package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.StudentStatus;
import com.ssgl.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/status/")
public class StateController {

    @Autowired
    public StateService stateService;
    @RequestMapping(value = "managerStudentState")
    public String managerStudentState() {
        return "status";
    }

    @ResponseBody
    @RequestMapping(value = "selectStatusPage",produces = "text/json;charset=utf-8")
    public String selectStatusPage(Integer page, Integer rows, HttpServletRequest request){
        try {
            Page<StudentStatus> result = stateService.selectStatesPage(page,rows,request);
            Map<String,Object> map = new HashMap<>();
            if(null!=result){
                map.put("total",result.getTotalRecord());
                map.put("rows",result.getList());
                return JSONObject.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
