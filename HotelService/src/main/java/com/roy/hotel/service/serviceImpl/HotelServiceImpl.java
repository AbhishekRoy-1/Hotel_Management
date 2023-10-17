package com.roy.hotel.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.roy.hotel.service.entities.Hotel;
import com.roy.hotel.service.entities.Rating;
import com.roy.hotel.service.exception.ResourceNotFoundException;
import com.roy.hotel.service.repository.HotelRepository;
import com.roy.hotel.service.service.HotelService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService  {
    private final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final EntityManager entityManager;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RestTemplate restTemplate;
    public HotelServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Hotel> getAll() {
        List<Hotel> hotels = hotelRepository.findAll();
        for (Hotel hotel : hotels) {
            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/hotels/"+hotel.getId(), Rating[].class);
            hotel.setRatings(List.of(ratingsOfUser));
        }
        return hotels;
    }
    @Override
    public Hotel create(Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> getHotelById(Long id) throws JsonProcessingException {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with ID: " + id + " not found!"));
        logger.info("{}",hotel);
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/hotels/"+id, Rating[].class);
        hotel.setRatings(List.of(ratingsOfUser));
        return Optional.ofNullable(hotel);
    }
    @Override
    public Hotel updateHotelById(Long id, Hotel updateHotel) throws Exception {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel existingHotel = hotelOptional.get();

            if (updateHotel.getName() != null) {
                existingHotel.setName(updateHotel.getName());
            }
            if (updateHotel.getAbout() != null) {
                existingHotel.setAbout(updateHotel.getAbout());
            }
            if (updateHotel.getLocation() != null) {
                existingHotel.setLocation(updateHotel.getLocation());
            }
            if (updateHotel.getNumber() != null) {
                existingHotel.setNumber(updateHotel.getNumber());
            }

            // Save the updated course
            return hotelRepository.save(existingHotel);
        } else {
            throw new Exception("Hotel not found with id: " + id);
        }
    }

    @Override
    public void deleteHotelById(Long id) {
        this.hotelRepository.deleteById(id);
    }
}
