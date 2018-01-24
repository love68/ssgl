package com.ssgl.mapper;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 1:20
 */

import com.ssgl.bean.City;
import com.ssgl.bean.County;

import java.util.List;

public interface CustomerAddressMapper {
    List<City> selectCitiesByProvinceId(String provinceid);

    List<County> selectCountiesByCityId(String cityid);
}
