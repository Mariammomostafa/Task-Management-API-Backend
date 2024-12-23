package com.taskSystem.service.employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskSystem.Dto.TaskDto;
import com.taskSystem.entity.Task;
import com.taskSystem.entity.User;
import com.taskSystem.enums.TaskStatus;
import com.taskSystem.repository.TaskRepository;
import com.taskSystem.repository.UserRepository;
import com.taskSystem.utils.JwtUtils;

import jakarta.persistence.EntityNotFoundException;


@Service
public class EmployeeServiceImpl  implements EmployeeService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	private JwtUtils jwtUtils;
	
	private TaskRepository taskRepository;
	
	public EmployeeServiceImpl(JwtUtils jwtUtils, TaskRepository taskRepository) {
		super();
		this.jwtUtils = jwtUtils;
		this.taskRepository = taskRepository;
	}


	@Override
	public User getUserByToken(String token) {
		
		String email =jwtUtils.getUsernameFromToken(token);
		User user = userRepository.findByEmail(email).get();
		
		return user;
	}


	@Override
	public List<TaskDto> getTasksByUserId(String token) {
		User user=getUserByToken(token);
		if(user != null) {
			return  taskRepository.findAllByUserId(user.getId()).stream()
			.sorted(Comparator.comparing(Task::getDuedate))
			.map(Task::getTaskDto).collect(Collectors.toList());
		}
		throw new EntityNotFoundException("User Not Found");
	}


	@Override
	public TaskDto updateTask(long id, String status) {
		 Task task= taskRepository.findById(id).get();
		 if(task != null){
			 task.setTaskStatus(mapStringToStatus(status));
		   return taskRepository.save(task).getTaskDto();
		 }
		throw new EntityNotFoundException("Task Not Found");
	}
	
	
	private TaskStatus mapStringToStatus(String status) {
		return switch (status) {
			case  "PENDING" -> TaskStatus.PENDING;
			case  "COMPLETED" -> TaskStatus.COMPLETED;
			case  "INPROGRESS" -> TaskStatus.INPROGRESS;
			case  "DEFERRED" -> TaskStatus.DEFERRED;
			default  -> TaskStatus.CANCELED;
	};
	

}

}
