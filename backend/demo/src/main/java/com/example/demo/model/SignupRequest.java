package com.example.demo.model;

public class SignupRequest {
    private int userIdd;
    public int getUserIdd() {
        return userIdd;
    }

    public void setUserIdd(int userIdd) {
        this.userIdd = userIdd;
    }

    private String name;
    private String email;
    private String password;
    private UserRole role;
    private Integer departmentId;  // Add this field
    private String specialtiy;
    public void setSpeciality(String speciality) {
        this.specialtiy = speciality;
    }

    public String getSpeciality() {
        return specialtiy;
    }

    // Add getter and setter for departmentId
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    // Other getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}