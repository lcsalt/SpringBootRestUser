package com.appsdeveloperblog.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appsdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
		public final AuthenticationManager authenticationManager;

		public AuthenticationFilter(AuthenticationManager authenticationManager) {
			this.authenticationManager = authenticationManager;
		}
		
		
		public Authentication attemptAuthentication(javax.servlet.http.HttpServletRequest request,
                javax.servlet.http.HttpServletResponse response) throws AuthenticationException{
			try {
				UserLoginRequestModel creds = new ObjectMapper()
											  .readValue(request.getInputStream(), UserLoginRequestModel.class);
				
				return authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								creds.getEmail(),
								creds.getPassword(),
								new ArrayList<>())
						);
			}catch(IOException e){
				throw new RuntimeException(e);
			}
			
		}
		
		
	
}
