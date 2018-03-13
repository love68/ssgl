package com.ssgl.service;

import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Room;

import java.util.List;

public interface RoomService {
    /**
     * 分页查询房间信息
     * @param page 要查询的页号
     * @param rows 查询多少条记录
     * @return
     * @throws Exception
     */
    Page<Room> selectRoomPage(Integer page, Integer rows) throws Exception;

    Result deleteRooms(List<String> ids) throws Exception;

    /**
     * 更新房间信息
     * @param room
     * @throws Exception
     */
    void updateRoom(Room room) throws Exception;

    void addRoom(Room room) throws Exception;
}
