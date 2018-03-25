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
import com.ssgl.util.MD5Utils;
import com.ssgl.util.StringUtils;
import com.ssgl.util.Util;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @RequiresPermissions("toFloorUI")
    @RequestMapping(value = "toUserUI")
    public String toFloorUI() {
        return "administrator";
    }
    @RequiresPermissions("addUser")
    @ResponseBody
    @RequestMapping(value = "addUser")
    public Result addUser(TUser user) {
        try {
            user.setId(String.valueOf(Util.makeId()));
            user.setPassword(MD5Utils.md5(user.getPassword()));
            return userService.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error", "添加失败");
        }
    }
    @RequiresPermissions("selectUsersPage")
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
    @RequiresPermissions("deleteUsers")
    @ResponseBody
    @RequestMapping(value="deleteUsers")
    public Result deleteUsers(String ids){
        try {
            return userService.deleteUsers(StringUtils.stringConvertList(ids));
        }catch (Exception e){
            e.printStackTrace();
            return new Result("error", "删除失败");
        }
    }
    @RequiresPermissions("updateUser")
    @ResponseBody
    @RequestMapping(value="updateUser")
    public Result updateUser(TUser user){
        try {
            return userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("error", "修改失败");
        }
    }
}
