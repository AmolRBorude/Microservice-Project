package com.jwtex.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtex.example.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Long>{

	Optional<Employee> findByEmpCodeAndCompanyName(String empCode, String companyName);

}
