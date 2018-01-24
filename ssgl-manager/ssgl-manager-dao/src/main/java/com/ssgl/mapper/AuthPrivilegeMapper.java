package com.ssgl.mapper;

import com.ssgl.bean.AuthPrivilege;
import com.ssgl.bean.AuthPrivilegeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthPrivilegeMapper {
    int countByExample(AuthPrivilegeExample example);

    int deleteByExample(AuthPrivilegeExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuthPrivilege record);

    int insertSelective(AuthPrivilege record);

    List<AuthPrivilege> selectByExample(AuthPrivilegeExample example);

    AuthPrivilege selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuthPrivilege record, @Param("example") AuthPrivilegeExample example);

    int updateByExample(@Param("record") AuthPrivilege record, @Param("example") AuthPrivilegeExample example);

    int updateByPrimaryKeySelective(AuthPrivilege record);

    int updateByPrimaryKey(AuthPrivilege record);
}