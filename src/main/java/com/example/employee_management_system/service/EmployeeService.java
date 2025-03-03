package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepo.findById(employeeId);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepo.findById(id).map(employee -> {
            employee.setEmployeeId(updatedEmployee.getEmployeeId());
            employee.setName(updatedEmployee.getName());
            employee.setDateOBirth(updatedEmployee.getDateOBirth());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setSalary(updatedEmployee.getSalary());
            return employeeRepo.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public List<Employee> findEmployeesByDepartment(String department) {
        return employeeRepo.findByDepartment(department);
    }

    public double averageSalary() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
    }

    public double averageAge() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().mapToDouble()
    }
}
