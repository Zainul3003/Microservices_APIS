package com.microservice.user.service.services;

import java.util.List;

import com.microservice.user.service.entities.User;

public interface UserService {
	
	//create
	User saveUser(User user);
	
	//Get all user
	List<User> getAllUser();
	
	//Get user
	User getUser(int userId);
	
	//Delete
	void deleteUser(int userId);
}
