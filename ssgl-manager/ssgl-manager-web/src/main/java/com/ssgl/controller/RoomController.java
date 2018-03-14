package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Room;
import com.ssgl.service.RoomService;
import com.ssgl.util.StringUtils;
import com.ssgl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    @ResponseBody
    @RequestMapping(value = "selectRoomsPage",produces = "text/json;utf-8")
    public String selectRoomsPage(Integer page,Integer rows){
        try {
            Page<Room> result = roomService.selectRoomPage(page,rows);
            Map<String,Object> map = new HashMap<>();
            map.put("total",result.getTotalRecord());
            map.put("rows",result.getList());
            return JSONObject.toJSONString(map);
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    @RequestMapping(value = "toManagerRoomUI")
    public String toManagerRoomUI(){
        return "room";
    }

    @ResponseBody
    @RequestMapping(value = "addRoom")
    public Result addRoom(Room room){
        try {
            room.setId(Util.makeId());
            roomService.addRoom(room);
            return new Result("ok","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("ok","添加失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "editRoom")
    public Result editRoom(Room room){
        try {
            roomService.updateRoom(room);
            return new Result("ok","修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("ok","修改失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "deleteRooms")
    public Result deleteRooms(HttpServletRequest request){
        try {
            return roomService.deleteRooms(StringUtils.stringConvertList(request.getParameter("ids")));
//            return roomService.deleteRoom(request.getParameter("ids"));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error","删除失败");
        }
    }
}
