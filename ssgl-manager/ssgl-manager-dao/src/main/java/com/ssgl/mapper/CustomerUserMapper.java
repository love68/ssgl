package com.ssgl.mapper;

import com.ssgl.bean.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerUserMapper {
    List<TUser> selectUsers(@Param("username") String username);
}
