package com.ssgl.mapper;

import com.ssgl.bean.RoomRank;
import com.ssgl.bean.RoomRankExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomRankMapper {
    int countByExample(RoomRankExample example);

    int deleteByExample(RoomRankExample example);

    int deleteByPrimaryKey(String id);

    int insert(RoomRank record);

    int insertSelective(RoomRank record);

    List<RoomRank> selectByExample(RoomRankExample example);

    RoomRank selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoomRank record, @Param("example") RoomRankExample example);

    int updateByExample(@Param("record") RoomRank record, @Param("example") RoomRankExample example);

    int updateByPrimaryKeySelective(RoomRank record);

    int updateByPrimaryKey(RoomRank record);
}