package com.example.studentinformationmanagement.model;

public class Student {
    private String name;
    private String code;
    private boolean isDelete;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public Student(String name, String code,boolean isDelete) {
        this.name = name;
        this.code = code;
        this.isDelete=isDelete;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
