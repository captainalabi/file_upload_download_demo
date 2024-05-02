package com.alabi.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alabi.app.entity.Employee;
import com.alabi.app.service.EmployeeService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<Employee> employeeList = employeeService.listEmployee();
		model.addAttribute("employeeList", employeeList);
		return "index";
	}
	
	@PostMapping("/upload")
	public String fileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		Employee employee = new Employee();
		String fileName = file.getOriginalFilename();
		employee.setProfilePicture(fileName);
		employee.setContent(file.getBytes());
		employee.setSize(file.getSize());
		employeeService.create(employee);
		model.addAttribute("success", "File uploaded successfully!");
		return "index";
	}
	
	@GetMapping("/downlodfile")
	public void downloadFile(@RequestParam("id") long id, HttpServletResponse response,
			Model model)throws IOException  {
		Optional<Employee> tempEmployee = employeeService.findEmployeeByID(id);
		if(tempEmployee != null) {
			Employee employee = tempEmployee.get();
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename = " + employee.getProfilePicture();
		response.setHeader(headerKey, headerValue);
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(employee.getContent());
		outputStream.close();
		}
	}
	
	@GetMapping("/image")
	public void showImage(@RequestParam("id") long id, HttpServletResponse response,
			Optional<Employee> employee) throws IOException {
		employee = employeeService.findEmployeeByID(id); 
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/pdf");
		response.getOutputStream().write(employee.get().getContent());
		response.getOutputStream().close();
	}
}
