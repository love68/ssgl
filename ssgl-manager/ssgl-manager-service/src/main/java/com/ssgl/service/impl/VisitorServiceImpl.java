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
import com.ssgl.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        return new Result("ok", "添加成功");
    }

    @Override
    public Page<Visiter> selectVisitorsPage(Integer page, Integer rows, HttpServletRequest request) throws Exception {
        PageHelper.startPage(page, rows);
        VisiterExample example = new VisiterExample();
        String name = request.getParameter("name");
        String visitStudentName = request.getParameter("visitStudentName");
        String startVisitTime = request.getParameter("startVisitTime");
        if(null==startVisitTime||startVisitTime.trim().equals("")){
            startVisitTime="1949-10-01 12:49:10";
        }
        String endVisitTime = Util.getDateTime(request.getParameter("endVisitTime"));
        List<Visiter> visiters = customerVisitorMapper.selectAllVisitor(name,visitStudentName,startVisitTime,endVisitTime);
        /*if (null != name && name.trim().length()>0) {
            example.createCriteria().andNameEqualTo(name);
        }
        List<Visiter> visiters = visiterMapper.selectByExample(example);*/
        if (null != visiters && visiters.size() > 0) {
            PageInfo<Visiter> pageInfo = new PageInfo<Visiter>(visiters);
            Page<Visiter> result = new Page<>();
            result.setTotalRecord((int) pageInfo.getTotal());
            result.setList(pageInfo.getList());
            return result;
        }
        return null;
    }

    @Override
    public List<Visiter> exportVisitor() {
        VisiterExample example = new VisiterExample();
        return visiterMapper.selectByExample(example);
    }
}
