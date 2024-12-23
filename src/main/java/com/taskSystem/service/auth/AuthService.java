package com.taskSystem.service.auth;

import com.taskSystem.Dto.SignupRequest;
import com.taskSystem.Dto.UserDto;

public interface AuthService {
	
	 UserDto signupUser(SignupRequest signupRequest);
	 
	 boolean findByEmail(String email);
	

}
