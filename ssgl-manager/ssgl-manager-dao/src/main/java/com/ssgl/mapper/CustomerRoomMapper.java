package com.ssgl.mapper;

import com.ssgl.bean.Result;

import java.util.List;

public interface CustomerRoomMapper {
    public Result deleteRooms(List<String> ids);
}
