package com.ssgl.controller;
/*
 * 功能:学生相关操作
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2017/12/30 0030
 * Time: 23:53
 */

import com.alibaba.fastjson.JSONObject;
import com.ssgl.bean.Page;
import com.ssgl.bean.Result;
import com.ssgl.bean.Student;
import com.ssgl.service.StudentService;
import com.ssgl.util.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    public StudentService studentService;

    @RequiresPermissions("managerStudent")
    @RequestMapping("managerStudent")
    public String managerStudent() {
        return "student";
    }

    @RequiresPermissions("changeStudentRoom")
    @ResponseBody
    @RequestMapping(value = "changeStudentRoom")
    public Result changeStudentRoom(String ids) {
        try {
            return studentService.changeStudentRoom(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error", "交换失败");
        }
    }

    @RequiresPermissions("exportStudent")
    @RequestMapping(value = "exportStudent")
    public void exportStudent(HttpServletRequest request, HttpServletResponse response, String sid, String name, String sex, String age, String entranceTime, String graduateTime, String faculty, String roomNumber, String duty) {
        try {
            name = request.getParameter("name");
            List<Student> students = studentService.exportStudent(request, response, sid, name, sex, age, entranceTime, graduateTime, faculty, roomNumber, duty);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            HSSFSheet sheet = workbook.createSheet("学生信息表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("学生考试成绩");
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 17));

            HSSFRow r = sheet.createRow(1);
            r.createCell(0).setCellValue("主键");
            r.createCell(1).setCellValue("学号");
            r.createCell(2).setCellValue("学生姓名");
            r.createCell(3).setCellValue("年龄");
            r.createCell(4).setCellValue("性别");
            r.createCell(5).setCellValue("毕业时间");
            r.createCell(6).setCellValue("家庭联系电话");
            r.createCell(7).setCellValue("入学时间");
            r.createCell(8).setCellValue("是否是本科生");
            r.createCell(9).setCellValue("是否是毕业生");
            r.createCell(10).setCellValue("宿舍号");
            r.createCell(11).setCellValue("楼号");
            r.createCell(12).setCellValue("床号");
            r.createCell(13).setCellValue("家庭住址");
            r.createCell(14).setCellValue("手机号码");
            r.createCell(15).setCellValue("职务");
            r.createCell(16).setCellValue("院系");
            r.createCell(17).setCellValue("照片");
//        for(int i = 2;i<students.size();i++){
//            HSSFRow hssfRow  = sheet.createRow(i);//创建第i行
//            for(int j=0;i<18;j++){
//                HSSFCell c=hssfRow.createCell(i);
//            }
//        }

            for (Student student : students) {
                HSSFRow h = sheet.createRow(sheet.getLastRowNum() + 1);
                h.createCell(0).setCellValue(student.getId());
                h.createCell(1).setCellValue(student.getSid());
                h.createCell(2).setCellValue(student.getName());
                h.createCell(3).setCellValue(student.getAge());
                h.createCell(4).setCellValue(student.getSex());
                h.createCell(5).setCellValue(student.getGraduateTime());
                h.createCell(6).setCellValue(student.getHomePhone());
                h.createCell(7).setCellValue(student.getEntranceTime());
                h.createCell(8).setCellValue(student.getIsUndergraduate());
                h.createCell(9).setCellValue(student.getIsGraduate());
                h.createCell(10).setCellValue(student.getRoomNumber());
                h.createCell(11).setCellValue(student.getDormitoryNo());
                h.createCell(12).setCellValue(student.getBedNo());
                h.createCell(13).setCellValue(student.getAddress());
                h.createCell(14).setCellValue(student.getPhone());
                h.createCell(15).setCellValue(student.getDuty());
                h.createCell(16).setCellValue(student.getFaculty());
                h.createCell(17).setCellValue(student.getIcon());
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/msexcel");

            String agent = request.getHeader("User-Agent");
            String filename = FileUtils.encodeDownloadFilename("学生信息表.xls", agent);
            response.setHeader("content-disposition", "attachment;filename=" + filename);

            workbook.write(out);
            out.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresPermissions("selectStudentsPage")
    @ResponseBody
    @RequestMapping(value = "selectStudentsPage", produces = "text/json;charset=utf-8")
    public String selectStudentsPage(Integer page, Integer rows, HttpServletRequest request) {
        try {
            Page<Student> page1 = studentService.selectStudentsPage(page, rows, request);
            Map<String, Object> map = new HashMap<>();
            if (null != page1) {
                map.put("total", page1.getTotalRecord());
                map.put("rows", page1.getList());
                return JSONObject.toJSONString(map);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresPermissions("selectStudentInfo")
    @RequestMapping(value = "selectStudentInfo")
    public ModelAndView selectStudentInfo(String id) {
        try {
            Student student = studentService.selectStudentInfo(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("StudentInfo");
            modelAndView.addObject("student", student);
            return modelAndView;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresPermissions("updateStudent")
    @RequestMapping(value = "updateStudent")
    public String updateStudent(String id, String phone, String homePhone, String address, String bedNo, String dormitoryNo, String duty, String roomNumber, Integer age, HttpServletResponse response) {
        try {
            studentService.updateStudent(id, phone, homePhone, address, bedNo, dormitoryNo, duty, roomNumber, age);
            Page<Student> page = studentService.selectStudentsPage(1, 10, null);
            Map<String, Object> map = new HashMap<>();
            if (null != page) {
                map.put("total", page.getTotalRecord());
                map.put("rows", page.getList());
                String s = JSONObject.toJSONString(map);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter pw = response.getWriter();
                pw.write(s);
                pw.flush();
                pw.close();
                return "student";
            }
            return "student";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresPermissions("addStudent")
    @ResponseBody
    @RequestMapping(value = "addStudent")
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
                             @RequestParam(value = "icon") MultipartFile icon) {

        try {
            return studentService.addStudent(sid,
                    name,
                    age,
                    sex,
                    graduateTime,
                    homePhone,
                    entranceTime,
                    isUndergraduate,
                    isGraduate,
                    roomNumber,
                    dormitoryNo,
                    bedNo,
                    province,
                    city,
                    county,
                    phone,
                    duty,
                    faculty,
                    icon);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("error", "添加失败");
        }
    }
}

