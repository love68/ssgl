package com.ssgl.bean;
/*
 * 功能:自定义的floor
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/18 0018
 * Time: 10:52
 */

public class CustomFloor extends Floor {
    public Integer getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(Integer buildingNo) {
        this.buildingNo = buildingNo;
    }

    private Integer buildingNo;
}
