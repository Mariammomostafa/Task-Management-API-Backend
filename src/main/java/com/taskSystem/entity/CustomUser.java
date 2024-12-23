package com.taskSystem.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUser extends User{
	
	private final long userID;

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities, long userID) {
        super();
        this.userID = userID;
    }

}
