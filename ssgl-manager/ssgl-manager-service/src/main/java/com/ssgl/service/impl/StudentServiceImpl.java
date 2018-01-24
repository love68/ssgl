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
import com.ssgl.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    public StudentMapper studentMapper;

    @Override
    public Result addStudent(Student student) throws Exception {
        student.setId(Util.makeId());
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
