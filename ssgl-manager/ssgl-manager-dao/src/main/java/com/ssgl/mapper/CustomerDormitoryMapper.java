package com.ssgl.mapper;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 14:40
 */

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CustomerDormitoryMapper {
    String selectDormitory(@PathVariable(value = "building_no") String buildingNo);

    void deleteDormitories(List<String> ids);

    Integer selectLayerByBuidingNo(Integer buildingNo);

}
