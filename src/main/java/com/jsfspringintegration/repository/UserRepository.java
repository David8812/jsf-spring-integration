package com.jsfspringintegration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jsfspringintegration.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	List<User> findAll();
	
	User findByUserName(String username);
}
