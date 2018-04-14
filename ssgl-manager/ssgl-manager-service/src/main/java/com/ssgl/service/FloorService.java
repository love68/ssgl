package com.ssgl.service;
/*
 * 功能:楼层的业务接口
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/17 0017
 * Time: 21:21
 */

import com.ssgl.bean.CustomFloor;
import com.ssgl.bean.Floor;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FloorService {
    /**
     * 添加楼层方法
     * @param floor
     * @param building_no
     * @throws Exception
     */
    Result addFloor(Floor floor,Integer building_no) throws Exception;

    String selectAllFloors() throws Exception;

    Page<CustomFloor> selectFloorDormitories(Integer page, Integer rows,HttpServletRequest request) throws Exception;

    Result deleteFloors(String ids) throws Exception;

    String selectLayerByBuidingNo(Integer buildingNo) throws Exception;

    List<Floor> exportFloor();

}
