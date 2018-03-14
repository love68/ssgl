package com.ssgl.mapper;

import java.util.List;

public interface CustomerRoomMapper {
    void deleteRooms(List<String> ids);

    void deleteRoom(String ids);
}
