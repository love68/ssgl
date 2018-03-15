package com.ssgl.mapper;

import com.ssgl.bean.CustomFloor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomFloorMapper {
    List<CustomFloor> selectFloorDormitories(@Param("buildingNo") String buildingNo);

    void deleteFloorDormitories(List<Integer> ids);

    void deleteFloors(List<Integer> ids);

}