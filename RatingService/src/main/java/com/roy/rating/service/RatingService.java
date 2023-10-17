package com.roy.rating.service;


import com.roy.rating.entities.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Rating create (Rating rating);
    List<Rating> getAll();
    Optional<Rating> getRatingById(Long id) throws Exception;
    List<Rating> getRatingByUserId(Long userId) throws Exception;
    List<Rating> getRatingByHotelId(Long hotelId) throws Exception;

    Rating updateRatingById(Long id, Rating updateRating) throws Exception;
    void deleteRatingById(Long id);
}
