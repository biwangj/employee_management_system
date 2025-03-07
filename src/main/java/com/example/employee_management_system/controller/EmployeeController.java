package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee addedEmployee = employeeService.addEmployee(employee);
        return ResponseEntity.ok(addedEmployee);
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<Employee> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeService.getAllEmployees(pageable);
    }

    @GetMapping
    public Optional<Employee> getEmployyeById(Long id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/departments")
    public List<String> getAllDepartments() {
        return employeeService.getAllDepartments();
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String department) {
        List<Employee> employees = employeeService.getEmployeeByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary")
    public ResponseEntity<Double> averageSalary() {
        double avgSalary = employeeService.averageSalary();
        System.out.println("Average Salary: " + avgSalary); // Debugging log
        return ResponseEntity.ok(avgSalary);
    }

    @GetMapping("/age")
    public ResponseEntity<Double> averageAge() {
        double avgAge = employeeService.averageAge();
        System.out.println("Computed Average Age: " + avgAge); // Debugging log
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/age/{age}")
    @ResponseBody
    public ResponseEntity<List<Employee>> filterByAge(@PathVariable int age) {
        List<Employee> employees = employeeService.getEmployeesByAge(age);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("search") String search) {
        List<Employee> employees = employeeService.getEmployeesByPartialName(search);
        return ResponseEntity.ok(employees);
    }

}
