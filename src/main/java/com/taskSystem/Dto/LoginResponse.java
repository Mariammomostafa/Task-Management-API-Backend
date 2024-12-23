package com.taskSystem.Dto;

import com.taskSystem.enums.UserRole;

public class LoginResponse {
	
	private long id;
	
	private String token;
	
	private UserRole userRole;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	

}
