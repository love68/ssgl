package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Visiter;
import com.ssgl.service.VisitorService;
import com.ssgl.util.Util;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 23:09
 */
@Controller
@RequestMapping("/visitor/")
public class VisitorController {

    @Autowired
    public VisitorService visitorService;

    @RequiresPermissions("toManagerVisitorUI")
    @RequestMapping(value = "toManagerVisitorUI")
    public String toManagerVisitorUI(){
        return "visitor";
    }
    @RequiresPermissions("selectVisitorsPage")
    @ResponseBody
    @RequestMapping(value = "selectVisitorsPage" ,produces = "text/json;charset=utf-8")
    public String selectVisitorsPage(Integer page, Integer rows, HttpServletRequest request){
        try {
            Page<Visiter> result = visitorService.selectVisitorsPage(page,rows,request);
            Map<String,Object> map = new HashMap<>();
            if(null!=request){
                map.put("total",result.getTotalRecord());
                map.put("rows",result.getList());
                System.out.println(JSONObject.toJSONString(map));
                return JSONObject.toJSONString(map);
            }
            return JSONObject.toJSONString(map);
        } catch (Exception e) {
            throw new RuntimeException("出错了。。。");
        }
    }
    @RequiresPermissions("addVisitor")
    @ResponseBody
    @RequestMapping(value ="addVisitor")
    public Result addVisitor(Visiter visiter){
        try {
            visiter.setId(Util.makeId());
            return visitorService.addVisitor(visiter);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("error","添加失败");
        }
    }

}
