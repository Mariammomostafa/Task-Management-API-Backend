package com.taskSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;

import com.taskSystem.enums.UserRole;
import com.taskSystem.service.admin.AdminService;
import com.taskSystem.service.jwt.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
	
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private UserService userService;
	
		public WebSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userService = userService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http.csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults()) 
		    .authorizeHttpRequests(request ->request
		    		.requestMatchers("/api/**").permitAll()
		        		   // .requestMatchers("/api/auth/**").permitAll()
					       // .requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name())
					        //.requestMatchers("/api/employee/**").hasAnyAuthority(UserRole.EMPLOYEE.name())
					        .anyRequest().authenticated())
		                  
		                 //.csrf(AbstractHttpConfigurer::disable)
		        .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
			   .authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class );
                 
		     
		        return     http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService.userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	

}
