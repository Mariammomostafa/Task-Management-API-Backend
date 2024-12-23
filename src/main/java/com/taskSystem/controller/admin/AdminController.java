package com.taskSystem.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskSystem.Dto.CommentDto;
import com.taskSystem.Dto.TaskDto;
import com.taskSystem.service.admin.AdminService;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
	
	private AdminService adminService;
	
	public AdminController(AdminService adminService) {
		
		this.adminService = adminService;
	}


	@GetMapping("/users")
	public ResponseEntity<?> getAllUserd(){
		return ResponseEntity.ok(adminService.getAllUsers());
	}
	
	@PostMapping("/task")
	public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto){
		
		TaskDto createdTaskDto= adminService.creatTask(taskDto);
		
		if(createdTaskDto == null) 
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
	}
	
	@GetMapping("/tasks")
	public ResponseEntity<?> getAllTasks(){
		return ResponseEntity.ok(adminService.getAllTasks());
	}
	
	@DeleteMapping("/task/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable("id") long id){
		adminService.deleteTask(id);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/task/{id}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable("id") long id){
		return ResponseEntity.ok(adminService.getTaskById(id));
	}
	
	@PutMapping("/task/{id}")
	public ResponseEntity<?> updateTask(@PathVariable("id") long id , @RequestBody TaskDto taskDto){
		
		TaskDto updatedTask=adminService.updateTask(id, taskDto);
		if(updatedTask == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(updatedTask);
	}
	
	@GetMapping("/tasks/search/{title}")
	public ResponseEntity<List<TaskDto>> getTaskByTitle(@PathVariable String title){
		return ResponseEntity.ok(adminService.searchTaskByTitile(title));
	}
	
	                              ///////////////////////// Comment  //////////////////////////
	
	@PostMapping("/task/comment/{taskId}")
	public ResponseEntity<CommentDto> createCommnent(@PathVariable long taskId, @RequestHeader("Authorization") String token,@RequestParam String content){
		
		CommentDto commentDto= adminService.createComment(taskId ,content ,token);
		
		if(commentDto == null) 
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
	}
	
	@GetMapping("/comments/{taskId}")
	public ResponseEntity<List<CommentDto>> getCommentsByTaskId(@PathVariable long taskId){
		
		return ResponseEntity.ok(adminService.getCommentsByTaskId(taskId));
	}
	
	
	

}
