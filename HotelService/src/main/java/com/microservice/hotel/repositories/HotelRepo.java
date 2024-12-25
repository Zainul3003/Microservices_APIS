package com.microservice.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.hotel.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {

}
