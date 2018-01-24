package com.ssgl.mapper;

import com.ssgl.bean.Visiter;
import com.ssgl.bean.VisiterExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VisiterMapper {
    int countByExample(VisiterExample example);

    int deleteByExample(VisiterExample example);

    int deleteByPrimaryKey(String id);

    int insert(Visiter record);

    int insertSelective(Visiter record);

    List<Visiter> selectByExample(VisiterExample example);

    Visiter selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Visiter record, @Param("example") VisiterExample example);

    int updateByExample(@Param("record") Visiter record, @Param("example") VisiterExample example);

    int updateByPrimaryKeySelective(Visiter record);

    int updateByPrimaryKey(Visiter record);
}