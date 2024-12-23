package com.taskSystem.service.admin;

import java.util.List;

import com.taskSystem.Dto.CommentDto;
import com.taskSystem.Dto.TaskDto;
import com.taskSystem.Dto.UserDto;

public interface AdminService {
	
	List<UserDto> getAllUsers();
	
	TaskDto creatTask(TaskDto taskDto);
	
	List<TaskDto> getAllTasks();
	
	void deleteTask(long id);
	
	TaskDto getTaskById(long id);
	
	TaskDto updateTask(long id , TaskDto taskDto);
	
	List<TaskDto> searchTaskByTitile(String title);
	
	CommentDto createComment(long taskId , String content ,String token);
	
	List<CommentDto> getCommentsByTaskId(long taskId);
 	

}
