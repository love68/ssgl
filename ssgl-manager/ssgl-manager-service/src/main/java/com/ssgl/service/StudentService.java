package com.ssgl.service;
/*
 * 功能:学生相关的业务方法
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 13:40
 */

import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Student;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface StudentService {
    /**
     *
     * @param sid
     * @param name
     * @param age
     * @param sex
     * @param graduateTime
     * @param homePhone
     * @param entranceTime
     * @param isUndergraduate
     * @param isGraduate
     * @param roomNumber
     * @param dormitoryNo
     * @param bedNo
     * @param phone
     * @param duty
     * @param faculty
     * @param icon
     * @return
     * @throws Exception
     */
    Result addStudent(String sid,
                      String name,
                      Integer age,
                      Boolean sex,
                      Date graduateTime,
                      String homePhone,
                      String entranceTime,
                      Boolean isUndergraduate,
                      Boolean isGraduate,
                      String roomNumber,
                      String dormitoryNo,
                      String bedNo,
                      String province,
                      String city,
                      String county,
                      String phone,
                      String duty,
                      String faculty,
                      @RequestParam(value = "icon") MultipartFile icon) throws Exception;


    /**
     * 更新学生信息接口
     *
     * @param student
     * @return 返回状态信息
     * @throws Exception
     */
    Result editStudent(Student student) throws Exception;

    /**
     * 删除学生
     *
     * @param ids
     * @return
     * @throws Exception
     */
    Result deleteStudent(String[] ids) throws Exception;

    /**
     * 根据条件查询学生信息
     * @param sid
     * @param name
     * @param sex
     * @param age
     * @param entranceTime
     * @param graduateTime
     * @param faculty
     * @param dedNo
     * @param dormitoryNo
     * @param roomNumber
     * @param isUndergraduate
     * @return
     * @throws Exception
     */
    String selectStudent(String sid, String name, String sex, Integer age, String entranceTime, String graduateTime, String faculty, Integer dedNo, Integer dormitoryNo, Integer roomNumber, Boolean isUndergraduate) throws Exception;

    Page<Student> selectStudentsPage(Integer page, Integer rows, HttpServletRequest request) throws Exception;


}
