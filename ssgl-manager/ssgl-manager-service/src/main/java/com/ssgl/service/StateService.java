package com.ssgl.service;

import com.ssgl.bean.Page;
import com.ssgl.bean.StudentStatus;

import javax.servlet.http.HttpServletRequest;

public interface StateService {
    Page<StudentStatus> selectStatesPage(Integer currentPage, Integer pageSize, HttpServletRequest request) throws Exception;
}
