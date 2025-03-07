package com.example.employee_management_system.repo;

import com.example.employee_management_system.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);

    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findDistinctDepartments();

    List<Employee> findByDateOfBirthBefore(LocalDate date);
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Employee> findByNameContainingIgnoreCase(String name);
}
