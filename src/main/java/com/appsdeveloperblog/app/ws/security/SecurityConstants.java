package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	public static final long EPIRATION_TIME = 864000000; //10 dias
	public static final String TOKEN_PREFIX = "bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";

	
	public static String getTokenSecret() {
		
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		
		return appProperties.getTokenSecret();
		
	}
}
