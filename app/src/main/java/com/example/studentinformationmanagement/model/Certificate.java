package com.example.studentinformationmanagement.model;

public class Certificate {
    private String id;
    private String name;
    private String studentCode;
    private boolean isDelete;

    public Certificate() {
    }

    public Certificate(String id,String name, String studentCode, boolean isDelete) {
        this.id=id;
        this.name = name;
        this.studentCode = studentCode;
        this.isDelete = isDelete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
