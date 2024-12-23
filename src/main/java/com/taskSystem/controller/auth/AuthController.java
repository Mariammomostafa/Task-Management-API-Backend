package com.taskSystem.controller.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskSystem.Dto.LoginRequest;
import com.taskSystem.Dto.LoginResponse;
import com.taskSystem.Dto.SignupRequest;
import com.taskSystem.Dto.UserDto;
import com.taskSystem.entity.User;
import com.taskSystem.repository.UserRepository;
import com.taskSystem.service.auth.AuthService;
import com.taskSystem.service.jwt.UserService;
import com.taskSystem.utils.JwtUtils;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private UserService userService;
	
	@Autowired
	private AuthService authService;
		
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthController(UserService userService) {
		super();
		this.userService = userService;
	}


	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
		
		if(authService.findByEmail(signupRequest.getEmail()))
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Exists with this email : " + signupRequest.getEmail());
	 UserDto userDto=	authService.signupUser(signupRequest);
	 
	 if(userDto == null)
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Created");
	
	 return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		
		try {
			authenticationManager.authenticate
			            (new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword()));
		
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect Username or password !!!");
		}
		
		UserDetails userDetails= userService.userDetailsService().loadUserByUsername(loginRequest.getEmail());
		String token= jwtUtils.generateToken(userDetails);
		
		Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
		LoginResponse loginResponse = new LoginResponse();
		if(user.isPresent()) {
			loginResponse.setId(user.get().getId());
			loginResponse.setUserRole(user.get().getUserRole());
			loginResponse.setToken(token);
		}
		return loginResponse;
	}
	
	
	
	

}
