package com.appsdeveloperblog.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
		public final AuthenticationManager authenticationManager;

		public AuthenticationFilter(AuthenticationManager authenticationManager) {
			this.authenticationManager = authenticationManager;
		}
		
		@Override
		public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest request,
                javax.servlet.http.HttpServletResponse response) throws AuthenticationException{
			try {
				UserLoginRequestModel creds = new ObjectMapper()
											  .readValue(request.getInputStream(), UserLoginRequestModel.class);
				
				return authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								creds.getEmail().trim(),
								creds.getPassword(),
								new ArrayList<>())
						);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
			
		}
		
		//Si attemptAuthentication se resuelve exitosamente,
		@Override
		public void successfulAuthentication (HttpServletRequest request,
											  HttpServletResponse response,
											  FilterChain chain,
											  Authentication auth) throws IOException, ServletException{
			
			//Token jjwt 
			//String userName = ((User) auth.getPrincipal().getUsername());
			//String token = Jwts.builder()
			//			   .setSubject(userName)
			//			   .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EPIRATION_TIME))
			//			   .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
			//			   .compact();
			
			//Token JWT 0Auth -> https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
			String userName = (((User) auth.getPrincipal()).getUsername());
			String token = JWT.create()
					 		.withSubject(userName)
					 		.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EPIRATION_TIME))
					 		.sign(Algorithm.HMAC512(SecurityConstants.TOKEN_SECRET.getBytes()));
			
			UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
			UserDto userDto = userService.getUser(userName);
			
			response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
			response.addHeader("UserId", userDto.getUserId());
		}
		
		
	
}
