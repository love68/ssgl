package com.ssgl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Visiter;
import com.ssgl.bean.VisiterExample;
import com.ssgl.mapper.CustomerVisitorMapper;
import com.ssgl.mapper.VisiterMapper;
import com.ssgl.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    public VisiterMapper visiterMapper;
    @Autowired
    public CustomerVisitorMapper customerVisitorMapper;


    @Override
    public Result addVisitor(Visiter visiter) throws Exception {
        visiterMapper.insert(visiter);
        return new Result("ok","添加成功");
    }

    @Override
    public Page<Visiter> selectVisitorsPage(Integer page, Integer rows) throws Exception {
        PageHelper.startPage(page, rows);
//        VisiterExample example = new VisiterExample();
//        List<Visiter> visiters = visiterMapper.selectByExample(example);
        List<Visiter> visiters = customerVisitorMapper.selectAllVisitor();
        if (null != visiters && visiters.size() > 0) {
            PageInfo<Visiter> pageInfo = new PageInfo<Visiter>(visiters);
            Page<Visiter> result = new Page<>();
            result.setTotalRecord((int) pageInfo.getTotal());
            result.setList(pageInfo.getList());
            return result;
        }
        return null;
    }
}
