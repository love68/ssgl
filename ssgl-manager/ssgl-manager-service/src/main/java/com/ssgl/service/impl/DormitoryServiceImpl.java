package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/2 0002
 * Time: 1:07
 */

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.Dormitory;
import com.ssgl.bean.DormitoryExample;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.mapper.CustomerDormitoryMapper;
import com.ssgl.mapper.DormitoryMapper;
import com.ssgl.service.DormitoryService;
import com.ssgl.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    public DormitoryMapper dormitoryMapper;

    @Autowired
    public CustomerDormitoryMapper customerDormitoryMapper;

    @Override
    public List<Dormitory> exportDormitory() {
        DormitoryExample example = new DormitoryExample();
        return dormitoryMapper.selectByExample(example);
    }

    @Override
    public void addDormitory(Dormitory dormitory) throws Exception {
        dormitory.setId(Util.makeId());
        dormitoryMapper.insert(dormitory);
    }

    @Override
    public Page<Dormitory> selectAllDormitories(Integer page, Integer pageSize,HttpServletRequest request) throws Exception {
        PageHelper.startPage(page, pageSize);
        DormitoryExample example = new DormitoryExample();
        String buildingNo = request.getParameter("buildingNo");
        if(null!=buildingNo&&buildingNo.length()>0) {
            example.createCriteria().andBuildingNoEqualTo(Integer.valueOf(buildingNo));
        }
        List<Dormitory> dormitories = dormitoryMapper.selectByExample(example);
        if (null != dormitories && dormitories.size() > 0) {
            PageInfo<Dormitory> pageInfo = new PageInfo<Dormitory>(dormitories);
            Page<Dormitory> result = new Page<>();
            result.setTotalRecord((int) pageInfo.getTotal());
            result.setList(pageInfo.getList());
            return result;
        }
        return null;
    }
    @Override
    public String findAllDormitories() throws Exception {
        DormitoryExample example = new DormitoryExample();
        List<Dormitory> dormitories = dormitoryMapper.selectByExample(example);
        return JSONObject.toJSONString(dormitories);
    }

    @Override
    public Result deleteDormitories(List<String> ids) throws Exception {
        customerDormitoryMapper.deleteDormitories(ids);
        return new Result("ok", "删除成功");
    }

    @Override
    public Result editDormitories(Dormitory dormitory) throws Exception {
        //从数据库中查到原数据
        Dormitory targetDormitory = dormitoryMapper.selectByPrimaryKey(dormitory.getId());
        //将更改的信息写到对象中
        BeanUtils.copyProperties(dormitory, targetDormitory);
        //写回到数据库中
        dormitoryMapper.updateByPrimaryKey(targetDormitory);
        return new Result("ok", "修改成功");
    }
}
