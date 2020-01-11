package com.abani.rest.employees.service;

import com.abani.rest.employees.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    Employee edit(Employee employee);
    void delete(Employee employee);
}
