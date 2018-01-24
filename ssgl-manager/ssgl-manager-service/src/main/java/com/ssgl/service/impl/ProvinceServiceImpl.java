package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/31 0031
 * Time: 23:01
 */

import com.alibaba.fastjson.JSONArray;
import com.ssgl.bean.Province;
import com.ssgl.bean.ProvinceExample;
import com.ssgl.mapper.ProvinceMapper;
import com.ssgl.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    public ProvinceMapper provinceMapper;

    @Override
    public String selectAllProvinces() throws Exception {
        ProvinceExample example = new ProvinceExample();
        List<Province> provinces = provinceMapper.selectByExample(example);
        return null == provinces ? "": JSONArray.toJSONString(provinces);
    }
}
