package com.microservice.rating.service;

import java.util.List;

import com.microservice.rating.entities.Rating;

public interface RatingService {

	//create
	Rating create(Rating rating);
	
	//get all
	List<Rating> getRatings();
	
	//get all by user
	List<Rating> getRatingsByUserId(String userId);
	
	//get all by hotels
	List<Rating> getRatingsByHotelId(String hotelId);
}
