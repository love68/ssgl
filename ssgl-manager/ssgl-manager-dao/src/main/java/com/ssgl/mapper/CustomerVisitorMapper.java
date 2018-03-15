package com.ssgl.mapper;

import com.ssgl.bean.Visiter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerVisitorMapper {
    List<Visiter> selectAllVisitor(@Param("name")String name,@Param("visitStudentName")String visitStudentName, @Param("startVisitTime")String startVisitTime, @Param("endVisitTime")String endVisitTime);
}
