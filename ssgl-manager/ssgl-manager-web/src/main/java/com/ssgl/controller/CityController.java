package com.ssgl.controller;

import com.ssgl.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 0:22
 */
@Controller
@RequestMapping("/city/")
public class CityController {

    @Autowired
    public CityService cityService;
    @ResponseBody
    @RequestMapping(value = "selectCitiesByProvinceId",produces = "text/json;charset=UTF-8")
    public String selectCitiesByProvinceId(String provinceid){
        try {
            return cityService.selectCitiesByProvinceId(provinceid);
        }catch (Exception e){
            throw new RuntimeException("出错了。。。");
        }
    }
    @ResponseBody
    @RequestMapping(value = "selectCountiesByCityId",produces = "text/json;charset=UTF-8")
    public String selectCountiesByCityId(String cityid){
        try {
            return cityService.selectCountiesByCityId(cityid);
        }catch (Exception e){
            throw new RuntimeException("出错了。。。");
        }
    }
}
