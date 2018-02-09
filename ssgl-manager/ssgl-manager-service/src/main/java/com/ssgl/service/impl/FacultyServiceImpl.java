package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 14:13
 */

import com.alibaba.fastjson.JSONArray;
import com.ssgl.bean.Faculty;
import com.ssgl.bean.FacultyExample;
import com.ssgl.mapper.FacultyMapper;
import com.ssgl.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    public FacultyMapper facultyMapper;

    @Override
    public String selectAllFaculties() throws Exception {
        FacultyExample example = new FacultyExample();
        FacultyExample.Criteria criteria = example.createCriteria().andParentFacultyIdEqualTo("0");
        List<Faculty> faculties = facultyMapper.selectByExample(example);
        return null == faculties ? "" : JSONArray.toJSONString(faculties);
    }

    @Override
    public String selectAllFacultiesByFacultyId(String facultyid) {
        FacultyExample example = new FacultyExample();
        FacultyExample.Criteria criteria = example.createCriteria().andParentFacultyIdEqualTo(facultyid);
        List<Faculty> faculties = facultyMapper.selectByExample(example);
        return null == faculties ? "" : JSONArray.toJSONString(faculties);
    }
}
