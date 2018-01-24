package com.ssgl.bean;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/23 0023
 * Time: 19:51
 */

import java.util.List;

public class DatagridBean {
    private Long total; // ܼ¼
    private List<?> rows;// ص

    public DatagridBean() {
    }

    public DatagridBean(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
