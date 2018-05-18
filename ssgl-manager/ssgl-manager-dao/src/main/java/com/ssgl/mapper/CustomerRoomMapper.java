package com.ssgl.mapper;

import com.ssgl.bean.Room;

import java.util.List;

public interface CustomerRoomMapper {
    void deleteRooms(List<String> ids);

    void deleteRoom(String ids);

    List<String> selectRoomById(List<String> ids);

    List<Room> getRooms(Integer _parameter);
}
