package com.ssgl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.StudentStatus;
import com.ssgl.bean.StudentStatusExample;
import com.ssgl.mapper.StudentStatusMapper;
import com.ssgl.service.StateService;
import com.ssgl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    public StudentStatusMapper statusMapper;

    @Override
    public Result addStatus(StudentStatus studentStatus) throws Exception {
        studentStatus.setId(Util.makeId());
        statusMapper.insert(studentStatus);
        return new Result("ok","添加成功");
    }

    @Override
    public Page<StudentStatus> selectStatesPage(Integer currentPage, Integer pageSize, HttpServletRequest request) throws Exception {
        PageHelper.startPage(currentPage,pageSize);
        String sid = request.getParameter("sid") == null ? "":request.getParameter("sid");
        String name = request.getParameter("name") == null ? "":request.getParameter("name");

        StudentStatusExample example = new StudentStatusExample();
        if(sid.length()>0){
            example.createCriteria().andSidEqualTo(sid);
        }
        if(name.length()>0){
            example.createCriteria().andNameEqualTo(name);
        }
        List<StudentStatus> list = statusMapper.selectByExample(example);

        if(null!=list&&list.size()>0){
            PageInfo<StudentStatus> info = new PageInfo<StudentStatus>(list);
            Page<StudentStatus> page = new Page<>();
            page.setList(info.getList());
            page.setTotalRecord((int) info.getTotal());
            return page;
        }

        return null;
    }
}
