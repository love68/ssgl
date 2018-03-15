package com.ssgl.service;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/16 0016
 * Time: 21:30
 */

import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.TUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     * @throws Exception
     */
    TUser selectUserByUsername(String username) throws Exception;

    String login(TUser user, HttpServletRequest request,String j_captcha);

    Page<TUser> selectUsersPage(Integer page, Integer rows, HttpServletRequest request) throws Exception;

    Result addUser(TUser user) throws Exception;

    Result updateUser(TUser user) throws Exception;

    Result deleteUsers(List<String> ids) throws Exception;

}
