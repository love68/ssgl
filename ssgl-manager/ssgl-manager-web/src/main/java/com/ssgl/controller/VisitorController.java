package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Visiter;
import com.ssgl.service.VisitorService;
import com.ssgl.util.FileUtils;
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



    @RequestMapping(value = "exportVisitor")
    public void exportVisitor(HttpServletRequest request,HttpServletResponse response) {
        try {
            List<Visiter> visitors = visitorService.exportVisitor();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("访客信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("访客信息");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("姓名");
            r.createCell(1).setCellValue("到访时间");
            r.createCell(2).setCellValue("访问人");
            r.createCell(3).setCellValue("电话");
            r.createCell(4).setCellValue("到访事由");

            for (Visiter visiter : visitors) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(visiter.getName());
                h.createCell(1).setCellValue(visiter.getVisitTime());
                h.createCell(2).setCellValue(visiter.getVisitStudentName());
                h.createCell(3).setCellValue(visiter.getPhone());
                h.createCell(4).setCellValue(visiter.getContent());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("访客信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




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
