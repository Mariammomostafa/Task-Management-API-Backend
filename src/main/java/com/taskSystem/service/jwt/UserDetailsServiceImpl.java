package com.taskSystem.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskSystem.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserService{

	private  UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
				 return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found ....."));
			
			}
		};
	}

}
