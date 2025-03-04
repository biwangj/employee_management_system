package com.example.employee_management_system.repo;

import com.example.employee_management_system.model.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);
    List<Employee> findByDateOfBirthBefore(LocalDate date);
    List<Employee> findByName(String name);

    @Transactional
    void deleteEmployeeByName(Employee employee);
}
