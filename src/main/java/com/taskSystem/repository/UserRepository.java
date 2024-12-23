package com.taskSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.taskSystem.entity.User;
import com.taskSystem.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String username);

	Optional<User> findByUserRole(UserRole admin);

	
	
	 

}
