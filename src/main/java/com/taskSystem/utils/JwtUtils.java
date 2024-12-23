package com.taskSystem.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.taskSystem.entity.CustomUser;
import com.taskSystem.entity.User;
import com.taskSystem.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private UserRepository userRepository ;
	
	public JwtUtils(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Value(value = "${auth.secret}")
	private   String TOKEN_SECRET;
	
	/*
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>() , userDetails);
	}
	*/
	
	public String generateToken(UserDetails userDetails) {
	
		Map<String, Object> claims=new HashMap<>();
		claims.put("username", userDetails.getUsername());
		claims.put("password", userDetails.getPassword());
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				  .setIssuedAt(new Date(System.currentTimeMillis()))
				  .setExpiration(new Date(System.currentTimeMillis()+ 60*60*24*1000))
				  .signWith(setSigningKey(), SignatureAlgorithm.HS256)
				  .compact();
	}

	private  Key setSigningKey() {
		
		byte[] keyBytes =Decoders.BASE64.decode(TOKEN_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String username= getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()));
	}

	
	public   String getUsernameFromToken(String token) {
		 if (token.startsWith("Bearer ")) {
			 token = token.substring(7);
		    }
		
		return extractClaim(token, Claims::getSubject);
	}

	private boolean isTokenExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}

	
	private Date getExpirationDate(String token) {
		
		return extractClaim(token, Claims::getExpiration);
	}
	
	
	private  <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		
		 final Claims claims = getAllClaims(token);
		 return claimsResolver.apply(claims);
	}
	
	private  Claims getAllClaims(String token) {
		
		return Jwts.parserBuilder().setSigningKey(setSigningKey())
				.build().parseClaimsJws(token).getBody(); 
	}
	
	
		public User getUserByToken(String token) {
				
				String email =getUsernameFromToken(token);
				User user = userRepository.findByEmail(email).get();
				
				return user;
			}
			
		
	}
	


/*
 * 
 * public User getLoggedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			
			 User user = (User) authentication.getPrincipal();
			 Optional<User> optionalUser=userRepository.findById(user.getId());
			 
			 return optionalUser.orElse(null);
		}
		return null;
	}
	
 * */
