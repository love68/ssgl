package com.ssgl.controller;

import com.ssgl.bean.Dormitory;
import com.ssgl.bean.Result;
import com.ssgl.service.DormitoryService;
import com.ssgl.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 14:54
 */
@Controller
@RequestMapping("/dormitory/")
public class DormitoryController {

    @Autowired
    public DormitoryService dormitoryService;

    @RequestMapping("toDormitoryUI")
    public String toDormitoryUI(){
        return "dormitory";
    }

    @ResponseBody
    @RequestMapping(value = "addDormitory")
    public Result addDormitory(Dormitory dormitory){
        try {
            dormitoryService.addDormitory(dormitory);
            return new Result("ok","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","操作失败"+e.getCause());
        }
    }

    @ResponseBody
    @RequestMapping(value = "selectAllDormitories")
    public String selectAllDormitories(){
        try {
            return dormitoryService.selectAllDormitories();
        } catch (Exception e) {
            throw new RuntimeException("出错了。。。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "deleteDormitories")
    public Result deleteDormitories(HttpServletRequest request){
        try {
           return dormitoryService.deleteDormitories(StringUtils.stringConvertList(request.getParameter("ids")));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","删除失败");
        }
    }

    @ResponseBody
    @RequestMapping(value="editDormitory")
    public Result editDormitory(Dormitory dormitory){
        try {
            return dormitoryService.editDormitories(dormitory);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("error","修改失败");
        }
    }

}
