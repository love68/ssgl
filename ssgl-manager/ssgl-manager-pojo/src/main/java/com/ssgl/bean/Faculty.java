package com.ssgl.bean;

public class Faculty {
    private Integer id;

    private String faculty;

    private String facultyid;

    private String parentFacultyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty == null ? null : faculty.trim();
    }

    public String getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(String facultyid) {
        this.facultyid = facultyid == null ? null : facultyid.trim();
    }

    public String getParentFacultyId() {
        return parentFacultyId;
    }

    public void setParentFacultyId(String parentFacultyId) {
        this.parentFacultyId = parentFacultyId == null ? null : parentFacultyId.trim();
    }
}