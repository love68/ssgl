package com.ssgl.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.CustomFloor;
import com.ssgl.bean.Floor;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/17 0017
 * Time: 21:01
 */
@Controller
@RequestMapping("/floor/")
public class FloorController {

    @Autowired
    public FloorService floorService;

    @RequestMapping(value = "toFloorUI")
    public String toFloorUI(){
        return "floor";
    }

    @ResponseBody
    @RequestMapping(value = "selectAllFloors",produces = "text/json;charset=utf-8")
    public String selectAllFloors(){
        try {
           return floorService.selectAllFloors();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "deleteFloors")
    public Result deleteFloors(String ids){
        try {
            return floorService.deleteFloors(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("出错了");
        }
    }

    @ResponseBody
    @RequestMapping(value = "selectFloorDormitories",produces = "text/json;charset=utf-8")
    public String selectFloorDormitories(Integer page, Integer rows,HttpServletRequest request){
        try {
            Page<CustomFloor> p = floorService.selectFloorDormitories(page,rows,request);
            Map<String, Object> map = new LinkedHashMap<>();
            if(null!=p){
                map.put("total", p.getTotalRecord());
                map.put("rows", p.getList());
                return JSONArray.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "addFloor")
    public Result addFloor(Floor floor,Integer building_no){
        try {
            return floorService.addFloor(floor,building_no);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("出错了");
        }
    }

    @ResponseBody
    @RequestMapping(value = "getLayers")
    public String getLayers(HttpServletRequest request,Integer buildingNo){
        try {
            return floorService.selectLayerByBuidingNo(buildingNo);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("出錯了");
        }
    }


}
