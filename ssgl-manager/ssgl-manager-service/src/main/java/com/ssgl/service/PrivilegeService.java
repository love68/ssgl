package com.ssgl.service;

import com.ssgl.bean.AuthPrivilege;
import com.ssgl.bean.Page;

public interface PrivilegeService {
    Page<AuthPrivilege> selectPrivilegePage(Integer currentPage,Integer pageSize) throws Exception;
}
