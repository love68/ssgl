package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/5 0005
 * Time: 13:40
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.*;
import com.ssgl.mapper.*;
import com.ssgl.service.StudentService;
import com.ssgl.util.FastDFSClient;
import com.ssgl.util.StringUtils;
import com.ssgl.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${BASE_IMAGE_SERVER_URL}")
    private String BASE_IMAGE_SERVER_URL;

    @Autowired
    public ProvinceMapper provinceMapper;
    @Autowired
    public CityMapper cityMapper;
    @Autowired
    public CountyMapper countyMapper;
    @Autowired
    public StudentMapper studentMapper;
    @Autowired
    public CustomerStudentMapper customerStudentMapper;

    @Override
    public Result changeStudentRoom(String idlist) throws Exception {
        List<String> ids = StringUtils.stringConvertList(idlist);
        String id1 = ids.get(0);
        String id2= ids.get(1);
        Student s1 = studentMapper.selectByPrimaryKey(id1);
        String roomNum1 = s1.getRoomNumber();
        Student s2 = studentMapper.selectByPrimaryKey(id2);
        String roomNum2 = s2.getRoomNumber();
        s1.setRoomNumber(roomNum2);
        s2.setRoomNumber(roomNum1);
        studentMapper.updateByPrimaryKey(s1);
        studentMapper.updateByPrimaryKey(s2);
        return new Result("ok","交换成功！");
    }

    @Override
    public Page<Student> selectStudentsPage(Integer page, Integer rows, HttpServletRequest request) throws Exception {
        PageHelper.startPage(page, rows);
        String name = request.getParameter("name");
        String sid = request.getParameter("sid");
        String roomNumber = request.getParameter("roomNumber");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String entranceTime = request.getParameter("entranceTime");
        String graduateTime = request.getParameter("graduateTime");
        String duty = request.getParameter("duty");
        String faculty = request.getParameter("faculty");
        List<Student> students = customerStudentMapper.selectStudentsPage(name, sid, roomNumber, age, sex, entranceTime, graduateTime, duty,
                faculty);
        if (null != students && students.size() > 0) {
            PageInfo<Student> pageInfo = new PageInfo<Student>(students);
            Page<Student> result = new Page<>();
            result.setList(pageInfo.getList());
            result.setTotalRecord((int) pageInfo.getTotal());
            return result;
        }
        return null;
    }

    @Override
    public Student selectStudentInfo(String id) throws Exception {
//        StudentExample example = new StudentExample();
//        example.createCriteria().andIdEqualTo(id);
//        List<Student> students = studentMapper.selectByExample(example);
        Student student = studentMapper.selectByPrimaryKey(id);
        return student;
    }

    @Override
    public List<Student> exportStudent(HttpServletRequest request,HttpServletResponse response, String sid, String name, String sex, String age, String entranceTime, String graduateTime, String faculty, String roomNumber, String duty) throws Exception {
       return customerStudentMapper.selectStudentsPage(name, sid, roomNumber, age, sex, entranceTime, graduateTime, faculty, duty);
    }

    @Override
    public void updateStudent(String id, String phone, String homePhone, String address, String bedNo, String dormitoryNo, String duty, String roomNumber, Integer age) throws Exception {
        Student oldStudent = studentMapper.selectByPrimaryKey(id);
        oldStudent.setPhone(phone);
        oldStudent.setHomePhone(homePhone);
        oldStudent.setAddress(address);
        oldStudent.setBedNo(bedNo);
        oldStudent.setDormitoryNo(dormitoryNo);
        oldStudent.setDuty(duty);
        oldStudent.setRoomNumber(roomNumber);
        oldStudent.setAge(age);
        studentMapper.updateByPrimaryKey(oldStudent);
    }

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
        ProvinceExample example1 = new ProvinceExample();
        example1.createCriteria().andProvinceidEqualTo(province);
        String p = provinceMapper.selectByExample(example1).get(0).getProvince();

        CityExample example2 = new CityExample();
        example2.createCriteria().andCityidEqualTo(city);
        String c = cityMapper.selectByExample(example2).get(0).getCity();

        CountyExample example3 = new CountyExample();
        example3.createCriteria().andAreaidEqualTo(county);
        String c2 = countyMapper.selectByExample(example3).get(0).getArea();

        student.setAddress(p + " " + c + " " + c2);
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
        String extName = imageName.substring(imageName.lastIndexOf(".") + 1);
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:properties/Client.conf");
        String url = fastDFSClient.uploadFile(icon.getBytes(), extName);
        url = BASE_IMAGE_SERVER_URL + url;
        student.setIcon(url);
        System.out.println(url);
        studentMapper.insert(student);
        return new Result("ok", "添加成功");
    }

    @Override
    public Result editStudent(Student student) throws Exception {
        Student oldStudent = studentMapper.selectByPrimaryKey(student.getId());//从数据库中查询到的学生
        BeanUtils.copyProperties(student, oldStudent);
        return new Result("ok", "修改成功");
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
