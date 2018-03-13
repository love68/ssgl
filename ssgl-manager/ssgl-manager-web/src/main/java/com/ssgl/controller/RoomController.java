package com.ssgl.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ssgl.bean.Page;
import com.ssgl.bean.Room;
import com.ssgl.service.RoomService;
import com.ssgl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            Page<Room> rooms = roomService.selectRoomPage(page,rows);
            return JSONObject.toJSONString(rooms);
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    @RequestMapping(value = "toManagerRoomUI")
    public String toManagerRoomUI(){
        return "room";
    }

    @RequestMapping(value = "addRoom")
    public void addRoom(Room room){
        try {
            room.setId(Util.makeId());
            roomService.addRoom(room);
        } catch (Exception e) {
            throw new RuntimeException("出错了");
        }
    }
}
