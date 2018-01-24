package com.ssgl.bean;
/*
 * 功能:返回结果
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/2 0002
 * Time: 0:46
 */

public class Result {
    private String status;
    private String message;

    public Result() {
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
