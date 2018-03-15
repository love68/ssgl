package com.ssgl.controller;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/16 0016
 * Time: 21:00
 */

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.TUser;
import com.ssgl.service.UserService;
import com.ssgl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "toIndex")
    public String toIndex() {
        return "login";
    }

    @RequestMapping(value = "login")
    public String login(TUser user, HttpServletRequest request, String j_captcha) {
        return userService.login(user, request, j_captcha);
    }

    @RequestMapping(value = "toUserUI")
    public String toFloorUI() {
        return "administrator";
    }

    @ResponseBody
    @RequestMapping(value = "addUser")
    public Result addUser(TUser user) {
        try {
            user.setId(String.valueOf(Util.makeId()));
            System.out.println(user);
            return userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error", "添加失败");
        }
    }


    @ResponseBody
    @RequestMapping(value = "selectUsersPage", produces = "text/html;charset=utf-8")
    public String selectUsersPage(Integer page, Integer rows, HttpServletRequest request) {
        try {
            Page<TUser> result = userService.selectUsersPage(page, rows, request);
            Map<String, Object> map = new HashMap<>();
            if (null != result) {
                map.put("total", result.getTotalRecord());
                map.put("rows", result.getList());
                return JSONObject.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
