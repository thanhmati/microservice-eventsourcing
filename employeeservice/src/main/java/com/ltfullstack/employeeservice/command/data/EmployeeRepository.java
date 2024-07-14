package com.ltfullstack.employeeservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    List<Employee> findAllByIsDisciplined(Boolean isDisciplined);
}
