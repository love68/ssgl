package com.ssgl.bean;

public class Bed {
    private String id;

    private Boolean bedNo;

    private Boolean isOccupy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getBedNo() {
        return bedNo;
    }

    public void setBedNo(Boolean bedNo) {
        this.bedNo = bedNo;
    }

    public Boolean getIsOccupy() {
        return isOccupy;
    }

    public void setIsOccupy(Boolean isOccupy) {
        this.isOccupy = isOccupy;
    }
}