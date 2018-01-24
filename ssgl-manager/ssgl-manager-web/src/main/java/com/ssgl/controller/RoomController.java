package com.ssgl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping(value = "selectRoomsPage",produces = "text/json;utf-8")
    public String selectRoomsPage(){
        try {
            return null;
        }catch (Exception e){
            throw new RuntimeException("出错了");
        }
    }

    @RequestMapping(value = "toManagerRoomUI")
    public String toManagerRoomUI(){
        return "room";
    }

}
