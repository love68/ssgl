package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/16 0016
 * Time: 21:31
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.TUser;
import com.ssgl.bean.TUserExample;
import com.ssgl.mapper.CustomerUserMapper;
import com.ssgl.mapper.TUserMapper;
import com.ssgl.service.UserService;
import com.ssgl.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public TUserMapper userMapper;
    @Autowired
    public CustomerUserMapper customerUserMapper;

    /**
     * 根据邮箱查找用户
     * @param email
     * @return
     */
    public TUser selectUserByUsername(String email){
        TUserExample example = new TUserExample();
        TUserExample.Criteria criteria = example.createCriteria().andEmailEqualTo(email);
        List<TUser> users = userMapper.selectByExample(example);
        return users.size()>0?users.get(0):null;
    }

    /**
     * 用户登录方法
     * @param user 登录的用户
     * @param request
     * @param j_captcha 用户输入的验证码
     */
    @Override
    public String login(TUser user, HttpServletRequest request,String j_captcha) {
        //获取当前session中的验证码
        String checkcode = (String) request.getSession().getAttribute("key");
        if(null != checkcode && null != j_captcha && checkcode.equals(j_captcha)){
            //验证码匹配正确
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), MD5Utils.md5(user.getPassword()));
            try {
                subject.login(token);
            }catch (Exception e){
                e.printStackTrace();
                return "forward:toIndex.action";
            }
            TUser loginUser = (TUser) subject.getPrincipal();
            request.getSession().setAttribute("loginUser",loginUser);
            return "home";
        }else {
            return "login";
        }
    }

    @Override
    public Page<TUser> selectUsersPage(Integer page, Integer rows, HttpServletRequest request) throws Exception {
        PageHelper.startPage(page,rows);
        String username = request.getParameter("username");
        List<TUser> users = customerUserMapper.selectUsers(username);
        if(null!=users&&users.size()>0){
            PageInfo<TUser> info = new PageInfo<TUser>(users);
            Page<TUser> result = new Page<>();
            result.setTotalRecord((int)info.getTotal());
            result.setList(info.getList());
            return result;
        }
        return null;
    }

    @Override
    public Result addUser(TUser user) throws Exception {
        userMapper.insertSelective(user);
        return new Result("ok","添加成功");
    }


}
