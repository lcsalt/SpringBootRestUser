package com.appsdeveloperblog.app.ws.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> { //los generics son la entidad del repositorio y el tipo de dato del id
	UserEntity findUserByEmail(String email); //JPA automaticamente interpreta el find{Entity}By{Atributo}({tipoAtributo} atributo)
	UserEntity findUserByUserId(String userId);
}
