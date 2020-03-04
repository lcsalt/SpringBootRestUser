package com.appsdeveloperblog.app.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.app.ws.UserRepository;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
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
		while(userRepository.findUserByUserId(generatedId) != null) {
			generatedId = utils.generateUserId(30);
		}
		userEntity.setUserId(generatedId);
		
		//Encripto la contraseña con el bcryptpasswordencoder.encode
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		
		//Utilizo UserRepository para crear el user y lo asigno a otra entity para pasarla despues al dto y al response
		UserEntity storedUsedDetails = userRepository.save(userEntity);
		//copio lo guardado a un dto
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUsedDetails, returnValue);
		
		return returnValue;
	}

}
