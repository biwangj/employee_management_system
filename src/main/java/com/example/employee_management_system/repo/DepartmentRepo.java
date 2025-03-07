package com.example.employee_management_system.repo;

import com.example.employee_management_system.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {

    Optional<Department> findById(Long id);
}
