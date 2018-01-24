package com.ssgl.mapper;

import com.ssgl.bean.RolePrivilegeExample;
import com.ssgl.bean.RolePrivilegeKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePrivilegeMapper {
    int countByExample(RolePrivilegeExample example);

    int deleteByExample(RolePrivilegeExample example);

    int deleteByPrimaryKey(RolePrivilegeKey key);

    int insert(RolePrivilegeKey record);

    int insertSelective(RolePrivilegeKey record);

    List<RolePrivilegeKey> selectByExample(RolePrivilegeExample example);

    int updateByExampleSelective(@Param("record") RolePrivilegeKey record, @Param("example") RolePrivilegeExample example);

    int updateByExample(@Param("record") RolePrivilegeKey record, @Param("example") RolePrivilegeExample example);
}