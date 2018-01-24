package com.ssgl.controller;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/16 0016
 * Time: 21:00
 */

import com.ssgl.bean.TUser;
import com.ssgl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    /*
    @RequestMapping(value = "logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
        }
    }*/


}
