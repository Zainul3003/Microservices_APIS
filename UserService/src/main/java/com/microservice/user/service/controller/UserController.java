package com.microservice.user.service.controller;

import java.util.List;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.service.entities.User;
import com.microservice.user.service.payload.ApiResponse;
import com.microservice.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	//create
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User savedUser=userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
	int retryCount=1;
	
	//get single user
	@GetMapping("/{userId}")
//	@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	@Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
//	@RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable("userId") int userId){
		
		logger.info("Retry Count:{}",retryCount);
		retryCount++;
		
		User user=userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	//creating fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(int userId,Exception ex){
		logger.info("Fallback is executed because service is down: ",ex.getMessage());
		
		User user=User.builder()
				.email("dummy@gmail.com")
				.name("dummy")
				.about("This user i generated beacause some service is down")
				.userId(12345)
				.build(); 
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	//get all
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> users=userService.getAllUser();
		return ResponseEntity.ok(users);
	}
	
	//delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int userId){
		userService.deleteUser(userId);
		return new ResponseEntity<>(new ApiResponse("Deleted Successfuly",true, null),HttpStatus.OK);
	}
}
