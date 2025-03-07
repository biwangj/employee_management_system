package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDepartment) {
        return departmentRepo.findById(id)
                .map(department -> {
                    department.setName(updatedDepartment.getName());
                    return departmentRepo.save(department);
                })
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }

    public Optional<Department> getDepartmentById(Long id){
        return departmentRepo.findById(id);
    }
}
