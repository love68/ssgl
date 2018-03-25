package com.ssgl.mapper;

import com.ssgl.bean.StudentStatus;
import com.ssgl.bean.StudentStatusExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentStatusMapper {
    int countByExample(StudentStatusExample example);

    int deleteByExample(StudentStatusExample example);

    int deleteByPrimaryKey(String id);

    int insert(StudentStatus record);

    int insertSelective(StudentStatus record);

    List<StudentStatus> selectByExample(StudentStatusExample example);

    StudentStatus selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StudentStatus record, @Param("example") StudentStatusExample example);

    int updateByExample(@Param("record") StudentStatus record, @Param("example") StudentStatusExample example);

    int updateByPrimaryKeySelective(StudentStatus record);

    int updateByPrimaryKey(StudentStatus record);
}