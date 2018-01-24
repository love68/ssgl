package com.ssgl.bean;

public class Visiter {
    private String id;

    private String visiterId;

    private String name;

    private String visitTime;

    private String visitStudentName;

    private String phone;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVisiterId() {
        return visiterId;
    }

    public void setVisiterId(String visiterId) {
        this.visiterId = visiterId == null ? null : visiterId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime == null ? null : visitTime.trim();
    }

    public String getVisitStudentName() {
        return visitStudentName;
    }

    public void setVisitStudentName(String visitStudentName) {
        this.visitStudentName = visitStudentName == null ? null : visitStudentName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}