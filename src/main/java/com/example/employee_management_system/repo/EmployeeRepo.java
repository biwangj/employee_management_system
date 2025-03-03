package com.example.employee_management_system.repo;

import com.example.employee_management_system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);
    List<Employee> findByDateOfBirthBefore(LocalDate date);
}
