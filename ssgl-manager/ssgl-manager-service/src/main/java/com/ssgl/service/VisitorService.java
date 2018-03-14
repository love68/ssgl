package com.ssgl.service;

import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Visiter;

public interface VisitorService {
    Result addVisitor(Visiter visiter) throws Exception;

    Page<Visiter> selectVisitorsPage(Integer page,Integer rows) throws Exception;

}
