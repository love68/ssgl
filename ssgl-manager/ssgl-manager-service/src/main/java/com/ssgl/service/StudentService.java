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
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface StudentService {

    List<Student> selectStudentsByRoomNumber(List<String> roomNumers);

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

    void updateStudent(String id,String phone,String homePhone,String address,String bedNo,String dormitoryNo,String duty,String roomNumber,Integer age) throws Exception;

    /**
     * 删除学生
     *
     * @param ids
     * @return
     * @throws Exception
     */
    Result deleteStudent(List<String> ids) throws Exception;


    Page<Student> selectStudentsPage(Integer page, Integer rows, HttpServletRequest request) throws Exception;

    Student selectStudentInfo(String id) throws Exception;


    List<Student>  exportStudent(HttpServletRequest request, HttpServletResponse response, String sid, String name, String sex, String age, String entranceTime, String graduateTime, String faculty, String roomNumber, String duty) throws Exception;

    Result changeStudentRoom(String idlist) throws Exception;

    Boolean checkStudentSid(String sid);

}
