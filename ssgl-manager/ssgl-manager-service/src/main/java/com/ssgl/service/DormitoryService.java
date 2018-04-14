package com.ssgl.service;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/2 0002
 * Time: 1:07
 */

import com.ssgl.bean.Dormitory;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DormitoryService {

    List<Dormitory> exportDormitory();

    /**
     * 添加宿舍楼
     * @param dormitory
     * @throws Exception
     */
    void addDormitory(Dormitory dormitory) throws Exception;

    /**
     * 查询所有的宿舍楼
     * @return
     * @throws Exception
     */
    Page<Dormitory> selectAllDormitories(Integer page, Integer pageSize,HttpServletRequest request) throws Exception;

    String findAllDormitories() throws Exception;


    /**
     * 删除宿舍楼
     * @param ids
     * @return
     * @throws Exception
     */
    Result deleteDormitories(List<String> ids) throws Exception;

    /**
     * 修改宿舍楼的信息
     * @param dormitory
     * @return
     * @throws Exception
     */
    Result editDormitories(Dormitory dormitory) throws Exception;

}
