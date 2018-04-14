package com.ssgl.controller;

import com.alibaba.fastjson.JSONArray;
import com.ssgl.bean.*;
import com.ssgl.service.FloorService;
import com.ssgl.util.FileUtils;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/17 0017
 * Time: 21:01
 */
@Controller
@RequestMapping("/floor/")
public class FloorController {

    @Autowired
    public FloorService floorService;

    @RequiresPermissions("toFloorUI")
    @RequestMapping(value = "toFloorUI")
    public String toFloorUI(){
        return "floor";
    }

    @ResponseBody
    @RequestMapping(value = "selectAllFloors",produces = "text/json;charset=utf-8")
    public String selectAllFloors(){
        try {
           return floorService.selectAllFloors();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequiresPermissions("deleteFloors")
    @RequestMapping(value = "deleteFloors")
    public Result deleteFloors(String ids){
        try {
            return floorService.deleteFloors(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("出错了");
        }
    }

    @ResponseBody
    @RequestMapping(value = "selectFloorDormitories",produces = "text/json;charset=utf-8")
    public String selectFloorDormitories(Integer page, Integer rows,HttpServletRequest request){
        try {
            Page<CustomFloor> p = floorService.selectFloorDormitories(page,rows,request);
            Map<String, Object> map = new LinkedHashMap<>();
            if(null!=p){
                map.put("total", p.getTotalRecord());
                map.put("rows", p.getList());
                return JSONArray.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @RequiresPermissions("addFloor")
    @ResponseBody
    @RequestMapping(value = "addFloor")
    public Result addFloor(Floor floor,Integer building_no){
        try {
            return floorService.addFloor(floor,building_no);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("出错了");
        }
    }

    @ResponseBody
    @RequestMapping(value = "getLayers")
    public String getLayers(HttpServletRequest request,Integer buildingNo){
        try {
            return floorService.selectLayerByBuidingNo(buildingNo);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("出錯了");
        }
    }

    @RequestMapping(value = "exportFloor")
    public void exportFloor(HttpServletRequest request,HttpServletResponse response) {
        try {
            List<Floor> floors = floorService.exportFloor();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("楼层信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("楼层信息");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("主键");
            r.createCell(1).setCellValue("楼层的房间数");
            r.createCell(2).setCellValue("第几层");
            r.createCell(3).setCellValue("楼层的学生数");
            r.createCell(4).setCellValue("剩余房间数");

            for (Floor floor : floors) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(floor.getId());
                h.createCell(1).setCellValue(floor.getRoomNumber());
                h.createCell(2).setCellValue(floor.getLayer());
                h.createCell(3).setCellValue(floor.getStudents());
                h.createCell(4).setCellValue(floor.getSpaces());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("楼层信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
