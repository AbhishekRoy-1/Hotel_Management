package com.roy.hotel.service.service;

import com.roy.hotel.service.entities.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel create (Hotel hotel);
    List<Hotel> getAll();
    Optional<Hotel> getHotelById(Long id) throws Exception;
    Hotel updateHotelById(Long id, Hotel updateHotel) throws Exception;
    void deleteHotelById(Long id);
}
