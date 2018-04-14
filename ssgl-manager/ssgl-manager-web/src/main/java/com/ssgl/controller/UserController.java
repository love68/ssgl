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
import com.ssgl.util.FileUtils;
import com.ssgl.util.MD5Utils;
import com.ssgl.util.StringUtils;
import com.ssgl.util.Util;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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


    @RequestMapping(value = "exportUser")
    public void exportUser(HttpServletRequest request,HttpServletResponse response) {
        try {
            List<TUser> users = userService.exportUser();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("管理员信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("管理员信息");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("姓名");
            r.createCell(1).setCellValue("生日");
            r.createCell(2).setCellValue("性别");
            r.createCell(3).setCellValue("电话");
            r.createCell(4).setCellValue("评价");
            r.createCell(5).setCellValue("邮箱");

            for (TUser user : users) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(user.getUsername());
                h.createCell(1).setCellValue(user.getBirthday());
                h.createCell(2).setCellValue(user.getGender());
                h.createCell(3).setCellValue(user.getTelephone());
                h.createCell(4).setCellValue(user.getRemark());
                h.createCell(5).setCellValue(user.getEmail());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("管理员信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }







}
