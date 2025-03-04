package com.example.employee_management_system.controller;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String viewEmployees(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Employee> employees;

        if(search != null && !search.isEmpty()) {
            employees = employeeService.getEmployeeByName(search);
        }else {
            employees =employeeService.getAllEmployees();
        }

        model.addAttribute("employees", employees);
        model.addAttribute("averageSalary", employeeService.averageSalary());
        model.addAttribute("averageAge", employeeService.averageAge());
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

    @GetMapping("/employees/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/dashboard";
    }

    @PostMapping("/employees/update/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employeeService.updateEmployee(id, employee);
        return "redirect:/dashboard"; // Redirect back to the dashboard after update
    }

    // Filter by Department
    @GetMapping("/filter/department")
    public String filterByDepartment(@RequestParam String department, Model model) {
        model.addAttribute("employees", employeeService.getEmployeeByDepartment(department));
        return "dashboard"; // Refreshes page with filtered employees
    }

    @GetMapping("/filter/age")
    public String filterByAge(@RequestParam int age, Model model) {
        List<Employee> filteredEmployees = employeeService.getEmployeesByAge(age);
        model.addAttribute("employees", filteredEmployees);
        return "dashboard"; // Refresh the dashboard with filtered results
    }

    @GetMapping("/employees/update-employee/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "update-employee"; // Ensure this file exists in `templates/`
        } else {
            return "dashboard"; // Redirect if employee not found
        }
    }

}

