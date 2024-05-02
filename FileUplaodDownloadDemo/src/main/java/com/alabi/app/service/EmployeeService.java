package com.alabi.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alabi.app.entity.Employee;
import com.alabi.app.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee create(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public List<Employee> listEmployee() {
	return employeeRepository.findAll();	
	}
	
	public Optional<Employee> findEmployeeByID(long id) {
		return employeeRepository.findById(id);
	} 
}
