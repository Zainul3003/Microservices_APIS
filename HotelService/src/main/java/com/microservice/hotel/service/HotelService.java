package com.microservice.hotel.service;

import java.util.List;

import com.microservice.hotel.entities.Hotel;

public interface HotelService {
	
	//create
	Hotel create(Hotel hotel);
	
	//get all
	List<Hotel> getAll();
	
	//get one 
	Hotel get(String id);
}
