package com.taskSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskSystem.Dto.TaskDto;
import com.taskSystem.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findAllByTitleContaining(String title);

	List<Task> findAllByUserId(long id);
	
	

}
