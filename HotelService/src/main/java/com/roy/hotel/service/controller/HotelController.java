package com.roy.hotel.service.controller;

import com.roy.hotel.service.entities.Hotel;
import com.roy.hotel.service.exception.ResourceNotFoundException;
import com.roy.hotel.service.repository.HotelRepository;
import com.roy.hotel.service.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    @PostMapping("/")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = hotelService.create(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }
    @GetMapping("/{hotelId}")
    public ResponseEntity<Optional<Hotel>> getSingleHotel(@PathVariable Long hotelId) throws Exception {
        Optional<Hotel> hotel = hotelService.getHotelById(hotelId);
        if(hotel.isEmpty()){
            throw new ResourceNotFoundException("Hotel not found " + hotelId);
        }
        return ResponseEntity.ok(hotel);
    }
    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getAllHotel()  {
        List<Hotel> allHotel = hotelService.getAll();

        return ResponseEntity.ok(allHotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable("hotelId") Long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if(hotel.isPresent()){
            this.hotelService.deleteHotelById(hotelId);
            return ResponseEntity.ok("Hotel with ID " + hotelId + " has been successfully deleted.");

        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<Object> updateHotel (@PathVariable("hotelId") Long hotelId, @RequestBody Hotel updatedHotel) throws Exception {
        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            Hotel updated = this.hotelService.updateHotelById(hotelId, updatedHotel);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
