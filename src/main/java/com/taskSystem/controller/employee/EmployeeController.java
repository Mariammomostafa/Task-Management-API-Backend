package com.taskSystem.controller.employee;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskSystem.Dto.TaskDto;
import com.taskSystem.entity.User;
import com.taskSystem.service.employee.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	
	@GetMapping("/tasks")
	public ResponseEntity<List<TaskDto>>  getAllTasksByUserId(@RequestHeader("Authorization") String jwt) {
		
		  return ResponseEntity.ok(employeeService.getTasksByUserId(jwt));
		}
	
	
	@GetMapping("/task/{id}/{status}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable("id") long id 
			                                  , @PathVariable("status") String status){
		
		TaskDto taskDto = employeeService.updateTask(id, status);
		if(taskDto == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.ok(taskDto);
	}

}
