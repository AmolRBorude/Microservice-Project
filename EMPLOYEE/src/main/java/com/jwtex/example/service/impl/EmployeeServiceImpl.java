package com.jwtex.example.service.impl;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jwtex.example.exception.BadRequestException;
import com.jwtex.example.exception.ResourceNotFoundException;
import com.jwtex.example.model.Employee;
import com.jwtex.example.model.dto.EmployeeDto;
import com.jwtex.example.repository.EmployeeRepository;
import com.jwtex.example.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	
	private final EmployeeRepository employeeRepository;
	private final ModelMapper modelMapper;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) 
	{
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) 
	{
		if(employeeDto.getId() != null)
		{
			throw new BadRequestException("Employee already exists",HttpStatus.BAD_REQUEST);
		}
		Employee entity = modelMapper.map(employeeDto, Employee.class);
		Employee savedEntity = employeeRepository.save(entity);
	
		return modelMapper.map(savedEntity, EmployeeDto.class);
	}

	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) 
	{
		if(id == null || employeeDto.getId() == null)
		{
			throw new  BadRequestException("Please provide employee id",HttpStatus.BAD_REQUEST);
		}
		
		
		if (!Objects.equals(id, employeeDto.getId())) 
		{
	        throw new BadRequestException("Id mismatch",HttpStatus.BAD_REQUEST);
	    }
		
		employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Id not found", HttpStatus.NOT_FOUND));
		
		Employee entity = modelMapper.map(employeeDto, Employee.class);
		Employee updatedEmployee = employeeRepository.save(entity);
		
		return modelMapper.map(updatedEmployee, EmployeeDto.class);
	}

	@Override
	public void deleteEmployee(Long id) 
	{
	  Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id "+id, HttpStatus.NOT_FOUND));
	  employeeRepository.delete(employee);
	}

	@Override
	public EmployeeDto getSingleEmployee(Long id) 
	{	
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id "+id, HttpStatus.NOT_FOUND));
		
		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public List<EmployeeDto> getAllEmployees() 
	{	
		List<Employee> employees = employeeRepository.findAll();
		
		if(employees.isEmpty())
		{
			throw new ResourceNotFoundException("Employees Not found",HttpStatus.NOT_FOUND);
		}
		
		List<EmployeeDto> dtoList = employees.stream()
                .map(emp -> modelMapper.map(emp, EmployeeDto.class))
                .toList();		
		
		return dtoList;
	}

	@Override
	public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) 
	{
	    Employee employee =	employeeRepository.findByEmpCodeAndCompanyName(empCode , companyName).orElseThrow(() -> new ResourceNotFoundException("Employee not found with employee code "+ empCode,HttpStatus.NOT_FOUND));
		return modelMapper.map(employee, EmployeeDto.class);
	}
	

}
