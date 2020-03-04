package com.appsdeveloperblog.app.ws.ui.model.request;


public class UserDetailsRequestModel { 
//Se llama así porque es el modelo de clase que uso cuando me mandan desde el front un json para convertirlo primero a una class de java
//con el RequestBody. Es un paso intermedio. Request=solicitud(post, update,p.e)
	

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
