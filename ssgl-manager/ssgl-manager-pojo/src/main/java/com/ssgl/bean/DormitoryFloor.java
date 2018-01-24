package com.ssgl.bean;

public class DormitoryFloor {
    private Integer floorId;

    public DormitoryFloor(Integer floorId, Integer dormitoryNum) {
        this.floorId = floorId;
        this.dormitoryNum = dormitoryNum;
    }

    public DormitoryFloor() {
    }

    private Integer dormitoryNum;

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getDormitoryNum() {
        return dormitoryNum;
    }

    public void setDormitoryNum(Integer dormitoryNum) {
        this.dormitoryNum = dormitoryNum;
    }
}