package com.example.demo.dto;

public class DepartmentDTO {
    private int departmentId;
    private String name;
    private int headId; // ID of the department head (User)
    private String headName; // Name of the department head (optional)

    // Constructors
    public DepartmentDTO() {}

    public DepartmentDTO(int departmentId, String name, int headId, String headName) {
        this.departmentId = departmentId;
        this.name = name;
        this.headId = headId;
        this.headName = headName;
    }

    // Getters and Setters
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", headId=" + headId +
                ", headName='" + headName + '\'' +
                '}';
    }
}