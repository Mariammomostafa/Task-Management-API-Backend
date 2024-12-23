package com.taskSystem.service.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskSystem.Dto.SignupRequest;
import com.taskSystem.Dto.UserDto;
import com.taskSystem.entity.User;
import com.taskSystem.enums.UserRole;
import com.taskSystem.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void createAdminAccount() {
		
		Optional<User> admin = userRepository.findByUserRole(UserRole.ADMIN);
		if(admin.isEmpty()) {
			
			User user=new User();
			user.setName("admin");
			user.setEmail("admin@gmail.com");
			user.setUserRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			
			userRepository.save(user);
			System.out.println(" admin created Successfully !!");
		}else {
			System.out.println(" admin already exists !!");
		}
	}



	@Override
	public UserDto signupUser(SignupRequest signupRequest) {
		
		User user = new User();
		
		user.setName(signupRequest.getName());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setEmail(signupRequest.getEmail());
		user.setUserRole(UserRole.EMPLOYEE);
		
		   User createdUser= userRepository.save(user);
		
		return createdUser.getUserDto();
	}



	@Override
	public boolean findByEmail(String email) {
		
		return userRepository.findByEmail(email).isPresent();
	}



}
