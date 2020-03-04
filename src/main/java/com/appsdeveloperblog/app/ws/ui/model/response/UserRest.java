package com.appsdeveloperblog.app.ws.ui.model.response;

public class UserRest {
//Le puso Rest al final solo para guiarse que este es el modelo con el cual envía las respuestas desde el back al front
//Esta clase luego es convertida a json para que js lo lea.
//Es una clase diferente al de Request, porque obvio que en un response no le vas a devolver la contraseña o informacion sensible de la db
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	
	
	
}
