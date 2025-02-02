package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.DepartmentRepository;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.model.Department;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

  
    public Optional<Department> getDepartmentById(int departmentId) {
        return departmentRepository.findById(departmentId);
    }

    
    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    
    public Department updateDepartment(Department department) {
        if (departmentRepository.existsById(department.getDepartmentId())) {
            return departmentRepository.save(department);
        }
        return null; 
    }

    
    public void deleteDepartment(int departmentId) {
        departmentRepository.deleteById(departmentId);
    }
    // In your service or controller
public DepartmentDTO toDTO(Department department) {
    DepartmentDTO dto = new DepartmentDTO();
    dto.setDepartmentId(department.getDepartmentId());
    dto.setName(department.getName());
    if (department.getHead() != null) {
        dto.setHeadId(department.getHead().getUserId());
        dto.setHeadName(department.getHead().getName());
    }
    return dto;
}
}
