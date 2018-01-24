package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 0:17
 */

import com.alibaba.fastjson.JSONArray;
import com.ssgl.bean.City;
import com.ssgl.bean.County;
import com.ssgl.mapper.CustomerAddressMapper;
import com.ssgl.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    public CustomerAddressMapper customerAddressMapper;

    @Override
    public String selectCitiesByProvinceId(String provinceID) throws Exception {
        List<City> cities = customerAddressMapper.selectCitiesByProvinceId(provinceID);
        return null == cities ? "" : JSONArray.toJSONString(cities);
    }

    @Override
    public String selectCountiesByCityId(String cityid) throws Exception {
        List<County> counties = customerAddressMapper.selectCountiesByCityId(cityid);
        return null == counties ? "" : JSONArray.toJSONString(counties);
    }
}
