package com.microservice.rating.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.rating.entities.Rating;

public interface RatingRepo extends JpaRepository<Rating, String> {
	
	List<Rating> findByUserId(String userId);
	List<Rating> findByHotelId(String hotelId);
}
