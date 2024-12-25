package com.microservice.hotel.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.hotel.entities.Hotel;
import com.microservice.hotel.exceptions.ResourceNotFoundException;
import com.microservice.hotel.repositories.HotelRepo;
import com.microservice.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	private HotelRepo hotelRepo;
	@Override
	public Hotel create(Hotel hotel) {
		String hotelId=UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepo.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("hotel not found"));
	}

}
