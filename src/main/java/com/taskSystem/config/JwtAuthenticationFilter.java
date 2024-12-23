package com.taskSystem.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.taskSystem.service.jwt.UserDetailsServiceImpl;
import com.taskSystem.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private JwtUtils jwtUtils;
	 private UserDetailsServiceImpl userDetailsServiceImpl;
	 
	 public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsServiceImpl) {
		super();
		this.jwtUtils = jwtUtils;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header =request.getHeader("Authorization");
		
		if(StringUtils.isEmpty(header) || !StringUtils.startsWith(header, "Bearer ") ) {
			filterChain.doFilter(request, response);
			return;
		}
		String token= header.substring("Bearer ".length());
		String userEmail =jwtUtils.getUsernameFromToken(token);
		
		if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails= userDetailsServiceImpl.userDetailsService().loadUserByUsername(userEmail);
			
			if(jwtUtils.isTokenValid(token,userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authToken);
				SecurityContextHolder.setContext(context);
			}
		}
		
		filterChain.doFilter(request, response);
		
		
	}
	
	

}
