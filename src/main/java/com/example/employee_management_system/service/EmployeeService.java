package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Page<Employee> getAllEmployees(Pageable pageable) {
        Page<Employee> employees = employeeRepo.findAll(pageable);
        //for debug log
        System.out.println("Fetched Employees: " + employees.getContent());
        return employeeRepo.findAll(pageable);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepo.findById(employeeId);
    }

    public Page<Employee> getEmployeeByName(String search, Pageable pageable) {
        return employeeRepo.findByNameContainingIgnoreCase(search, pageable);
    }

    public List<Employee> getEmployeesByAge(int age) {
        // Calculate the date of birth threshold
        LocalDate cutoffDate = LocalDate.now().minusYears(age);
        return employeeRepo.findByDateOfBirthBefore(cutoffDate);
    }

    public List<Employee> getEmployeeByDepartment(String department) {
        return employeeRepo.findByDepartment(department);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepo.findById(id).map(employee -> {
            employee.setEmployeeId(updatedEmployee.getEmployeeId());
            employee.setName(updatedEmployee.getName());
            employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
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

    public List<String> getAllDepartments() {
        return employeeRepo.findDistinctDepartments();
    }

    public double averageSalary() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
    }

    public double averageAge() {
        List<Employee> employees = employeeRepo.findAll();
        return  (int) employees.stream().mapToInt(employee -> Period.between(employee.getDateOfBirth(),
                        LocalDate.now()).getYears()).average().orElse(0.0);
    }

    public List<Employee> getEmployeesByPartialName(String search) {
        return employeeRepo.findByNameContainingIgnoreCase(search);
    }
}
