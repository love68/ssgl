package com.ssgl.controller;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/31 0031
 * Time: 23:30
 */

import com.ssgl.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/province/")
public class ProvinceController {

    @Autowired
    public ProvinceService provinceService;

    @ResponseBody
    @RequestMapping(value = "selectProvinces",produces = "text/json;charset=UTF-8")
    public String selectProvinces(){
        try {
            return provinceService.selectAllProvinces();
        }catch (Exception e){
            throw new RuntimeException("出错了。。。");
        }
    }

}
