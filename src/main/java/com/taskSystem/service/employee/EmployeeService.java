package com.taskSystem.service.employee;

import java.util.List;

import com.taskSystem.Dto.TaskDto;
import com.taskSystem.entity.User;

public interface EmployeeService {
	
	 User  getUserByToken(String token);
	
	List<TaskDto> getTasksByUserId(String token);
	
	TaskDto updateTask(long id ,String status);

}
