package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.AuthPrivilege;
import com.ssgl.bean.Page;
import com.ssgl.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    public PrivilegeService privilegeService;

    @RequestMapping("toPrivilegeUI")
    public String toPrivilegeUI(){
        return "privilege";
    }

    @ResponseBody
    @RequestMapping(value="selectPrivilegePage",produces = "text/json;charset=utf-8")
    public String selectPrivilegePage(Integer page,Integer rows){
        try {
            Page<AuthPrivilege> result = privilegeService.selectPrivilegePage(page,rows);
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
