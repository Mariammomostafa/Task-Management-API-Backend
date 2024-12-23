 package com.taskSystem.service.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.taskSystem.Dto.CommentDto;
import com.taskSystem.Dto.TaskDto;
import com.taskSystem.Dto.UserDto;
import com.taskSystem.entity.Comment;
import com.taskSystem.entity.Task;
import com.taskSystem.entity.User;
import com.taskSystem.enums.TaskStatus;
import com.taskSystem.enums.UserRole;
import com.taskSystem.repository.CommentRepository;
import com.taskSystem.repository.TaskRepository;
import com.taskSystem.repository.UserRepository;
import com.taskSystem.utils.JwtUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImple implements AdminService{
	
	
	private  UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	public AdminServiceImple(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	public List<UserDto> getAllUsers() {
		
		return userRepository.findAll()
				.stream()
				.filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
				.map(User::getUserDto)
				.collect(Collectors.toList());
		
	}
	
	/*
	
	private LocalDateTime convertDateToLocal(String date) {
		DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-mm-dd '/' hh-mm-ss-ssss");
		LocalDateTime newDate= LocalDateTime.parse(date , formatter);
		return newDate;
	}
	
	private String convertDateToString(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd '/' hh-mm-ss-ssss");
		String text = date.format(formatter);
		System.out.println(text);
		return text;
	}

*/

	@Override
	public TaskDto creatTask(TaskDto taskDto) {
		
		Optional<User> optionalUser =userRepository.findById(taskDto.getEmployeeId());
		
		if(optionalUser.isPresent()) {
			
			Task task= new Task();
			
			task.setTitle(taskDto.getTitle());
			task.setDescription(taskDto.getDescription());
			task.setDuedate( new Date());
			task.setPriority(taskDto.getPriority());
			task.setTaskStatus(TaskStatus.INPROGRESS);
			task.setUser(optionalUser.get());
			
			 return taskRepository.save(task).getTaskDto();
			
		}
		return null;
	}



	@Override
	public List<TaskDto> getAllTasks() {
		List<Task> tasks=taskRepository.findAll();
		return  tasks.stream().map(Task::getTaskDto).collect(Collectors.toList());
				/*
				.stream()
				.sorted(Comparator.comparing(Task::getDuedate))
				.map(Task::getTaskDto)
				.collect(Collectors.toList());
				*/
	}



	@Override
	public void deleteTask(long id) {
		taskRepository.deleteById(id);
	}



	@Override
	public TaskDto getTaskById(long id) {
	
		Optional<Task> optionalTask= taskRepository.findById(id);
		
		return optionalTask.map(Task::getTaskDto).orElse(null);
	}



	@Override
	public TaskDto updateTask(long id, TaskDto taskDto) {
		Task optionalTask=taskRepository.findById(id).get();
		User user =userRepository.findById(taskDto.getEmployeeId()).get();
		if(optionalTask != null) {
			optionalTask.setTitle(taskDto.getTitle());
			optionalTask.setDescription(taskDto.getDescription());
			optionalTask.setPriority(taskDto.getPriority());
			optionalTask.setDuedate(taskDto.getDuedate());
			optionalTask.setTaskStatus(mapStringToStatus(String.valueOf(taskDto.getTaskStatus())));
			optionalTask.setUser(user);
			
			return  taskRepository.save(optionalTask).getTaskDto();
		}
		return null;
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



	@Override
	public List<TaskDto> searchTaskByTitile(String title) {
		
		return taskRepository.findAllByTitleContaining(title)
				.stream().sorted(Comparator.comparing(Task::getDuedate).reversed())
				.map(Task::getTaskDto).collect(Collectors.toList());
	}



	@Override
	public CommentDto createComment(long taskId, String content ,String token) {
		
		  Task task= taskRepository.findById(taskId).get();
		  User user= jwtUtils.getUserByToken(token);
		  
		  if(task != null && user != null ) {
				Comment comment = new Comment();
				comment.setContent(content);
				comment.setCreatedAt(new Date());
				comment.setTask(task);
				comment.setUser(user);
				//comment.setId(user.getId());
				
				return commentRepository.save(comment).getCommentDto();
		  }
		
		throw new EntityNotFoundException("User or Token not Found !!!");
	}



	@Override
	public List<CommentDto> getCommentsByTaskId(long taskId) {
		return commentRepository.findAllByTaskId(taskId).stream()
				.map(Comment::getCommentDto)
				.collect(Collectors.toList());
	
	}
	
}
