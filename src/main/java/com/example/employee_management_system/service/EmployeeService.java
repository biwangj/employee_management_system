package com.example.employee_management_system.service;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
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

    public List<Employee> getEmployeeByName(String name) {
        return employeeRepo.findByName(name);
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

    public void removeEmployee(Long id) {
        Optional<Employee> employeeOpt = employeeRepo.findById(id);
        if(employeeOpt.isPresent()) {
            Employee employee =employeeOpt.get();
            employeeRepo.deleteEmployeeByName(employee);
        }
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
        return  (int) employees.stream().mapToInt(employee -> Period.between(employee.getDateOfBirth(),
                        LocalDate.now()).getYears()).average().orElse(0.0);
    }
}
