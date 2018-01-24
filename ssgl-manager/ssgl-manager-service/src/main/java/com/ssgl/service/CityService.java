package com.ssgl.service;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 0:16
 */

public interface CityService {

    String selectCitiesByProvinceId(String provinceID) throws Exception;

    String selectCountiesByCityId(String cityid) throws Exception;
}
