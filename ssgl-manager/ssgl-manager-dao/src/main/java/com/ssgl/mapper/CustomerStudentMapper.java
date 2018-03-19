package com.ssgl.mapper;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 14:10
 */

import com.ssgl.bean.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerStudentMapper {
    List<Student> selectStudentsPage(@Param("name") String name,
                                     @Param("sid") String sid,
                                     @Param("roomNumber") String roomNumber,
                                     @Param("age") String age,
                                     @Param("sex") String sex,
                                     @Param("entranceTime") String entranceTime,
                                     @Param("graduateTime") String graduateTime,
                                     @Param("duty") String duty,
                                     @Param("faculty") String faculty
    );
}
