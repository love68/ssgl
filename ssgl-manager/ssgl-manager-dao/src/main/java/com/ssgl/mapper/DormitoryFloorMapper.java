package com.ssgl.mapper;

import com.ssgl.bean.DormitoryFloor;
import com.ssgl.bean.DormitoryFloorExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DormitoryFloorMapper {
    int countByExample(DormitoryFloorExample example);

    int deleteByExample(DormitoryFloorExample example);

    int insert(DormitoryFloor record);

    int insertSelective(DormitoryFloor record);

    List<DormitoryFloor> selectByExample(DormitoryFloorExample example);

    int updateByExampleSelective(@Param("record") DormitoryFloor record, @Param("example") DormitoryFloorExample example);

    int updateByExample(@Param("record") DormitoryFloor record, @Param("example") DormitoryFloorExample example);
}