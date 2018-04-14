package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Dormitory;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.service.DormitoryService;
import com.ssgl.util.FileUtils;
import com.ssgl.util.StringUtils;
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

    @RequiresPermissions("addDormitory")
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
    public String selectAllDormitories(Integer page, Integer rows,HttpServletRequest request){
        try {
            Page<Dormitory> result = dormitoryService.selectAllDormitories(page,rows,request);
            Map<String,Object> map = new HashMap<>();
            map.put("total",result.getTotalRecord());
            map.put("rows",result.getList());
            return JSONObject.toJSONString(map);
        } catch (Exception e) {
            throw new RuntimeException("出错了。。。");
        }
    }

    @ResponseBody
    @RequestMapping(value = "findAllDormitories")
    public String selectAllDormitories(){
        try {
            return dormitoryService.findAllDormitories();
        } catch (Exception e) {
            throw new RuntimeException("出错了。。。");
        }
    }
    @RequiresPermissions("deleteDormitories")
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

    @RequiresPermissions("editDormitory")
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

    @RequestMapping(value = "exportDormitory")
    public void exportDormitory(HttpServletRequest request,HttpServletResponse response) {
        try {
            List<Dormitory> dormitories = dormitoryService.exportDormitory();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("宿舍楼信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("宿舍楼信息");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("宿舍楼号");
            r.createCell(1).setCellValue("房间数");
            r.createCell(2).setCellValue("剩余房间数");
            r.createCell(3).setCellValue("学生数");
            r.createCell(4).setCellValue("楼层数");

            for (Dormitory dormitory : dormitories) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(dormitory.getBuildingNo());
                h.createCell(1).setCellValue(dormitory.getRooms());
                h.createCell(2).setCellValue(dormitory.getSurplus());
                h.createCell(3).setCellValue(dormitory.getStudents());
                h.createCell(4).setCellValue(dormitory.getFloors());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("宿舍楼信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
