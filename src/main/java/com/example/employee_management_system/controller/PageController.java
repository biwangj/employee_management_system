package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.Department;
import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repo.DepartmentRepo;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepo departmentRepo;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login/page")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login/page";
    }

    @GetMapping("/dashboard")
    public String viewEmployees(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage = employeeService.getAllEmployees(pageable);

        // Debugging statement
        System.out.println("Passing Employees to View: " + employeePage.getContent());

        model.addAttribute("employees", employeePage.getContent()); // Get list from Page
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());

        return "dashboard";
    }

    @GetMapping("/add-employee")
    public String showAddEmployee() {
        return "add-employee";
    }

    @GetMapping("/update-employee")
    public String showUpdateEmployee() {
        return "update-employee";
    }

    @GetMapping("/employee")
    public String addEmployeeView() {
        return "employee";
    }

    @PostMapping("/add")
    public String createEmployee(@ModelAttribute Employee employee) {
        employeeService.addEmployee(employee);
        return "redirect:/dashboard";
    }

    @GetMapping("add-department")
    public String addDepartment() {
        return "add-department";
    }

    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/dashboard"; // Redirect back to the dashboard after update
    }

//    @GetMapping("/filter/age")
//    public String filterByAge(@RequestParam int age, Model model) {
//        List<Employee> filteredEmployees = employeeService.getEmployeesByAge(age);
//        model.addAttribute("employees", filteredEmployees);
//        return "dashboard"; // Refresh the dashboard with filtered results
//    }

    @GetMapping("/employees/update-employee/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Department> departments = departmentRepo.findAll(); // Fetch departments

        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments); // Add departments list

        return "update-employee"; // Thymeleaf template name
    }

}

