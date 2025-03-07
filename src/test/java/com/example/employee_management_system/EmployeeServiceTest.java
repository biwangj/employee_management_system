package com.example.employee_management_system;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.employee_management_system.model.Employee;
import com.example.employee_management_system.repo.EmployeeRepo;
import com.example.employee_management_system.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmployeeId("111");
        employee.setName("JB");
        employee.setDateOfBirth(LocalDate.of(2000, 5, 25));
        employee.setDepartment("IT");
        employee.setSalary(254000.0);
    }

    @Test
    void testGetAllEmployees() {
        Page<Employee> employees = new PageImpl<>(List.of(employee));
        when(employeeRepo.findAll(any(Pageable.class))).thenReturn(employees);

        Page<Employee> result = employeeService.getAllEmployees(mock(Pageable.class));
        assertEquals(1, result.getTotalElements());
        verify(employeeRepo, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testAddEmployee() {
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);
        Employee savedEmployee = employeeService.addEmployee(employee);
        assertNotNull(savedEmployee);
        assertEquals("JB", savedEmployee.getName());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeRepo.findById(2L)).thenReturn(Optional.of(employee));
        Optional<Employee> foundEmployee = employeeService.getEmployeeById(2L);
        assertTrue(foundEmployee.isPresent());
        assertEquals("JB", foundEmployee.get().getName());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeRepo.findById(2L)).thenReturn(Optional.of(employee));
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        Employee updated = new Employee();
        updated.setEmployeeId("111");
        updated.setName("JB");
        updated.setDepartment("Admin");
        updated.setSalary(254000.0);
        updated.setDateOfBirth(LocalDate.of(2000, 5, 25));

        Employee result = employeeService.updateEmployee(2L, updated);
        assertEquals("JB", result.getName());
        assertEquals("Admin", result.getDepartment());
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeRepo).deleteById(2L);
        employeeService.deleteEmployee(2L);
        verify(employeeRepo, times(1)).deleteById(2L);
    }

    @Test
    void testAverageSalary() {
        Employee employee2 = new Employee();
        employee2.setSalary(11111.0); // Set a valid salary for the second employee

        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee, employee2));

        double expectedAverage = (employee.getSalary() + employee2.getSalary()) / 2;
        assertEquals(expectedAverage, employeeService.averageSalary());
    }

    @Test
    void testAverageAge() {
        Employee employee2 = new Employee();
        employee2.setDateOfBirth(LocalDate.of(1995, 5, 15)); // Set a valid birth date

        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee, employee2));

        double expectedAverage = (Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears() +
                Period.between(employee2.getDateOfBirth(), LocalDate.now()).getYears()) / 2.0;

        assertEquals(expectedAverage, employeeService.averageAge());
    }
}
