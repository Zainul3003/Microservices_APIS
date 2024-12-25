package com.microservice.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.service.entities.Hotel;
import com.microservice.user.service.entities.Rating;
import com.microservice.user.service.entities.User;
import com.microservice.user.service.exceptions.ResourceNotFoundExceptions;
import com.microservice.user.service.external_services.HotelService;
import com.microservice.user.service.repositories.UserRepo;
import com.microservice.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public User getUser(int userId) {
		//get single user from repo
		User user= userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundExceptions("User with id not found "));
		
		//fetch rating from Rating-Service
		//http://localhost:8083/ratings/users/1
		
		Rating[] ratingOfUSer=restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		
		List<Rating> ratings=Arrays.stream(ratingOfUSer).toList();
		logger.info("",ratings);
		
		List<Rating> ratingList=ratings.stream().map(rating ->{
			//API call to hotel service
			//http://localhost:8082/hotels/f3b8cc0a-71fc-4ff4-9f4b-24a9ad96d9f8
			
//			ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//			Hotel hotel=forEntity.getBody();
			
			Hotel hotel=hotelService.getHotel(rating.getHotelId());
			
			//set hotel rating
			rating.setHotel(hotel);
			
			//return rating
			return rating;
			
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public void deleteUser(int userId) {
		User user=userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundExceptions("User with id not found "));
		userRepo.delete(user);
	}

}
