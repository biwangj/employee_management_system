package com.example.employee_management_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table
@MappedSuperclass
public class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOBirth;
}
