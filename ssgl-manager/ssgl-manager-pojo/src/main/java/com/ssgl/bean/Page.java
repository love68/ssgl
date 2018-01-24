package com.ssgl.bean;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/1 0001
 * Time: 14:44
 */

import java.util.List;

public class Page<T> {
    private Integer currentPage;// 当前页号
    private Integer pageSize = 6;// 每页显示多少条数据,默认每页显示两条数据
    private Integer totalRecord;// 总共多少条数据
    private List<T> list;// 要存放的数据

    private Integer totalPage;// 总页数
    private Integer startPage;// jsp页面中的开始页号
    private Integer endPage;// jsp页面中的结束页号

    public Page() {
    }

    /**
     * @param currentPage
     * @param pageSize
     * @param totalRecord
     * @param list
     */
    public Page(Integer currentPage, Integer pageSize, Integer totalRecord, List list) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.list = list;

        // 计算总页数
        // 总记录数对分页大小取余
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }

        // 计算开始页
        // 计算结束页
        if (totalPage < 10) {
            startPage = 1;
            endPage = totalPage;
        } else {
            startPage = currentPage - 4;
            endPage = currentPage + 5;
            if (startPage < 1) {
                startPage = 1;
                endPage = 10;
            } else if (endPage > totalPage) {
                startPage = totalPage - 9;
                endPage = totalPage;
            }
        }
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

}
