package com.roy.rating.controller;

import com.roy.rating.entities.Rating;
import com.roy.rating.exception.ResourceNotFoundException;
import com.roy.rating.repository.RatingRepository;
import com.roy.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RatingRepository ratingRepository;
    @PostMapping("/")
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating){
        Rating rating1 = ratingService.create(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }
    @GetMapping("/{ratingId}")
    public ResponseEntity<Optional<Rating>> getSingleRating(@PathVariable Long ratingId) throws Exception {
        Optional<Rating> rating = ratingService.getRatingById(ratingId);
        if(rating.isEmpty()){
            throw new ResourceNotFoundException("Rating not found " + ratingId);
        }
        return ResponseEntity.ok(rating);
    }
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getByHotelId(@PathVariable Long hotelId) throws Exception {
        List<Rating> rating = ratingService.getRatingByHotelId(hotelId);
        if(rating.isEmpty()){
            throw new ResourceNotFoundException("Rating not found " + hotelId);
        }
        return ResponseEntity.ok(rating);
    }
    @GetMapping("user/{userId}")
    public ResponseEntity<List<Rating>> getByUserId(@PathVariable Long userId) throws Exception {
        List<Rating> rating = ratingService.getRatingByUserId(userId);
        if(rating.isEmpty()){
            throw new ResourceNotFoundException("Rating not found " + userId);
        }
        return ResponseEntity.ok(rating);
    }
    @GetMapping("/")
    public ResponseEntity<List<Rating>> getAllRating()  {
        List<Rating> allRating = ratingService.getAll();

        return ResponseEntity.ok(allRating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable("ratingId") Long ratingId) {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        if(rating.isPresent()){
            this.ratingService.deleteRatingById(ratingId);
            return ResponseEntity.ok("Rating with ID " + ratingId + " has been successfully deleted.");

        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Object> updateRating (@PathVariable("ratingId") Long ratingId, @RequestBody Rating updatedRating) throws Exception {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        if (rating.isPresent()) {
            Rating updated = this.ratingService.updateRatingById(ratingId, updatedRating);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
