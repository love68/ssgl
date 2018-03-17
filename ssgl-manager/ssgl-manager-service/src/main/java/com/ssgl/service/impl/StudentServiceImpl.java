package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 13:40
 */

import com.ssgl.bean.Result;
import com.ssgl.bean.Student;
import com.ssgl.mapper.StudentMapper;
import com.ssgl.service.StudentService;
import com.ssgl.util.FastDFSClient;
import com.ssgl.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${BASE_IMAGE_SERVER_URL}")
    private String BASE_IMAGE_SERVER_URL;

    @Autowired
    public StudentMapper studentMapper;

    @Override
    public Result addStudent(String sid,
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
                             @RequestParam(value = "icon") MultipartFile icon) throws Exception {
        Student student = new Student();
        student.setId(Util.makeId());
        student.setAddress(province+" "+city+" "+county);
        student.setAge(age);
        student.setBedNo(bedNo);
        student.setIsUndergraduate(isUndergraduate);
        student.setDormitoryNo(dormitoryNo);
        student.setDuty(duty);
        student.setEntranceTime(entranceTime);
        student.setFaculty(faculty);
        student.setGraduateTime(graduateTime);
        student.setHomePhone(homePhone);
        student.setIsGraduate(isGraduate);
        student.setSid(sid);
        student.setSex(sex);
        student.setRoomNumber(roomNumber);
        student.setPhone(phone);
        student.setName(name);

        String imageName = icon.getOriginalFilename();
        String extName = imageName.substring(imageName.lastIndexOf(".")+1);
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/Client.conf");
        String url = fastDFSClient.uploadFile(icon.getBytes(),extName);
        url =BASE_IMAGE_SERVER_URL+url;
        student.setIcon(url);
        System.out.println(url);
        studentMapper.insert(student);
        return new Result("ok","添加成功");
    }

    @Override
    public Result editStudent(Student student) throws Exception {
        Student oldStudent = studentMapper.selectByPrimaryKey(student.getId());//从数据库中查询到的学生
        BeanUtils.copyProperties(student,oldStudent);
        return new Result("ok","修改成功");
    }

    @Override
    public Result deleteStudent(String[] ids) throws Exception {
        return null;
    }

    @Override
    public String selectStudent(String sid, String name, String sex, Integer age, String entranceTime, String graduateTime, String faculty, Integer dedNo, Integer dormitoryNo, Integer roomNumber, Boolean isUndergraduate) throws Exception {
        return null;
    }
}
