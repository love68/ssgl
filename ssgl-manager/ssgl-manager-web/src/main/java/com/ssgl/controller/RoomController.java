package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Room;
import com.ssgl.service.RoomService;
import com.ssgl.util.FileUtils;
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

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 22:31
 */
@Controller
@RequestMapping("/room/")
public class RoomController {

    @Autowired
    public RoomService roomService;

    @RequiresPermissions("selectRoomsPage")
    @ResponseBody
    @RequestMapping(value = "selectRoomsPage",produces = "text/json;charset=utf-8")
    public String selectRoomsPage(Integer page,Integer rows, HttpServletRequest request){
        try {
            Page<Room> result = roomService.selectRoomPage(page,rows,request);
            Map<String,Object> map = new HashMap<>();
            map.put("total",result.getTotalRecord());
            map.put("rows",result.getList());
            return JSONObject.toJSONString(map);
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    @RequiresPermissions("toManagerRoomUI")
    @RequestMapping(value = "toManagerRoomUI")
    public String toManagerRoomUI(){
        return "room";
    }

    @RequiresPermissions("addRoom")
    @ResponseBody
    @RequestMapping(value = "addRoom")
    public Result addRoom(Room room){
        try {
            room.setId(Util.makeId());
            roomService.addRoom(room);
            return new Result("ok","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","添加失败");
        }
    }

    @RequiresPermissions("editRoom")
    @ResponseBody
    @RequestMapping(value = "editRoom")
    public Result editRoom(Room room){
        try {
            roomService.updateRoom(room);
            return new Result("ok","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","修改失败");
        }
    }

    @RequiresPermissions("deleteRooms")
    @ResponseBody
    @RequestMapping(value = "deleteRooms")
    public Result deleteRooms(HttpServletRequest request){
        try {
            return roomService.deleteRooms(StringUtils.stringConvertList(request.getParameter("ids")));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","删除失败");
        }
    }

    @RequestMapping(value = "exportRoom")
    public void exportRoom(HttpServletRequest request,HttpServletResponse response) {
        try {
            List<Room> rooms = roomService.exportRoom();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("房间信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("学生信息");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("主键");
            r.createCell(1).setCellValue("宿舍号");
            r.createCell(2).setCellValue("宿舍容量");
            r.createCell(3).setCellValue("实际人数");
            r.createCell(4).setCellValue("所属宿舍楼");
            r.createCell(5).setCellValue("得分");

            for (Room room : rooms) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(room.getId());
                h.createCell(1).setCellValue(room.getRoomNumber());
                h.createCell(2).setCellValue(room.getCapacity());
                h.createCell(3).setCellValue(room.getPeopleNum());
                h.createCell(4).setCellValue(room.getDormitoryNum());
                h.createCell(5).setCellValue(room.getScore());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("房间信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
