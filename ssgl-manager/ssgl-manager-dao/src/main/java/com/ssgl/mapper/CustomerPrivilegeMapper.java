package com.ssgl.mapper;

import java.util.List;

public interface CustomerPrivilegeMapper {
    List<String> selectPrivileges(String userid);
}
