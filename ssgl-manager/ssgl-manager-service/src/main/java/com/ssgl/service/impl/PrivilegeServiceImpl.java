package com.ssgl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.AuthPrivilege;
import com.ssgl.bean.AuthPrivilegeExample;
import com.ssgl.bean.Page;
import com.ssgl.mapper.AuthPrivilegeMapper;
import com.ssgl.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    public AuthPrivilegeMapper privilegeMapper;
    @Override
    public Page<AuthPrivilege> selectPrivilegePage(Integer currentPage, Integer pageSize) throws Exception {
        PageHelper.startPage(currentPage,pageSize);
        AuthPrivilegeExample example = new AuthPrivilegeExample();
        List<AuthPrivilege> privileges = privilegeMapper.selectByExample(example);
        if(null!=privileges&&privileges.size()>0){
            PageInfo<AuthPrivilege> info = new PageInfo<AuthPrivilege>(privileges);
            Page<AuthPrivilege> page = new Page<>();
            page.setTotalRecord((int) info.getTotal());
            page.setList(info.getList());
            return page;
        }
        return null;
    }
}
