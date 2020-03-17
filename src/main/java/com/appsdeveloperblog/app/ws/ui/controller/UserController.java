package com.appsdeveloperblog.app.ws.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserDetailsRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}")
	public UserRest getUser(@PathVariable String id){
		UserRest returnValue = new UserRest();
		UserDto userDto = userService.getUserById(id);
		BeanUtils.copyProperties(userDto, returnValue);
		return returnValue;
	}
	
	@PostMapping
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		//Este es el userRest que va a devolver la función, lo instancio
		UserRest returnValue = new UserRest();
		
		//Creo un userDto para que sirva de intermediario entre las capas, al cual le copio las propiedades del requestmodel(que vino de front)
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		//Utilizo el userService, para que le envíe la info del dto que le paso, para que cree en base a una entity, un registro en bd
		// luego asigno el user creado al userDto createdUser para después poder pasarlo a un response user (userRest)
		UserDto createdUser = userService.createUser(userDto);
		//acá efectivamente copio los datos del dto createduser al userrest returnValue creado antes. Así solo le devuelvo los datos que
		//comparte el dto con el userRest(todos los de UserRest) y no se devuelve info sensible, ya que todo ocurre dentro del backend 
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}
	
	@PutMapping
	public String updateUser() {
		return "update user was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete user was called";
	}

}
