package com.appsdeveloperblog.app.ws.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

@Service 
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	Utils utils;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserDto createUser(UserDto user) {
		//Para comprobar que el email ingresado no este en uso
		if(userRepository.findUserByEmail(user.getEmail()) != null) throw new RuntimeException("There is already an account with that address.");
		
		//Primero paso la data del userDto a un userEntity para que el repository lo lea
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		
		//genero un userId random y compruebo que no exista ya para otro usuario y lo asigno al nuevo usuario
		String generatedId = utils.generateUserId(30);
		while(userRepository.findUserByUserId(generatedId) != null) { //si no es null significa que alguien lo tiene ya, entonces creo otro
			generatedId = utils.generateUserId(30);
		}
		userEntity.setUserId(generatedId);
		
		//Encripto la contraseña con el bcryptpasswordencoder.encode
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		
		//Utilizo UserRepository para crear el user(metodo del crudrepository) y lo asigno a otra entity para pasarla despues al dto y al response
		UserEntity storedUsedDetails = userRepository.save(userEntity);
		//copio lo guardado a un dto
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUsedDetails, returnValue);
		
		return returnValue;
	}
	
	@Override
	public UserDto getUser(String email) {
		//Busco el email ingresado para corroborar que el user existe
		UserEntity userEntity = userRepository.findUserByEmail(email);
		//si no existe el email, devuelvo exception
		if(userEntity == null) throw new UsernameNotFoundException(email);
		//copio el userEntity encontrado a un dto
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		//devuelvo el dto
		return returnValue;
	}
	
	@Override
	public UserDto getUserById(String userId) {
		UserEntity userEntity = userRepository.findUserByUserId(userId);
		//si no existe el userid, devuelvo exception
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		//copio el userEntity encontrado a un dto
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		//devuelvo el dto
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Busco el email ingresado para corroborar que el user existe
		UserEntity userEntity = userRepository.findUserByEmail(email);
		//si no existe el email, devuelvo exception
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	

}
