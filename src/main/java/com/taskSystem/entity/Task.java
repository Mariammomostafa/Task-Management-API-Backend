package com.taskSystem.entity;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taskSystem.Dto.TaskDto;
import com.taskSystem.enums.TaskStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id ;
	private String title;
	
	private String description;
		
	private Date duedate;
	
	private String priority;
	
	private TaskStatus taskStatus;
	
	
	@ManyToOne(fetch = FetchType.LAZY , optional = false)
	@JoinColumn(name = "user_id" , nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDuedate() {
		return duedate;
	}


	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public TaskStatus getTaskStatus() {
		return taskStatus;
	}


	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	public TaskDto getTaskDto() {
		
		TaskDto taskDto= new TaskDto();
		taskDto.setId(id);
		taskDto.setTitle(title);
		taskDto.setDescription(description);
		taskDto.setDuedate(duedate);
		taskDto.setPriority(priority);
		taskDto.setTaskStatus(taskStatus);
		taskDto.setEmployeeId(user.getId());
		taskDto.setEmployeeName(user.getName());
		
		return taskDto;
		}
	
	
	

}
