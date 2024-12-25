package com.microservice.user.service.external_services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservice.user.service.entities.Rating;

@Service
@FeignClient(name = "RATINGSERVICE")
public interface RatingService {
	
	//post
	@PostMapping("/ratings")
	public Rating createRating(Rating values);
}
