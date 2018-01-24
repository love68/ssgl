package com.ssgl.mapper;

import com.ssgl.bean.CustomFloor;

import java.util.List;

public interface CustomFloorMapper {
    List<CustomFloor> selectFloorDormitories();

    void deleteFloorDormitories(List<Integer> ids);

    void deleteFloors(List<Integer> ids);

}