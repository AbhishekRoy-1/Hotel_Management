package com.roy.rating.serviceImpl;

import com.roy.rating.entities.Rating;
import com.roy.rating.repository.RatingRepository;
import com.roy.rating.service.RatingService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RatingServiceImpl implements RatingService {
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public List<Rating> getAll() {
        return this.ratingRepository.findAll();
    }
    @Override
    public Rating create(Rating rating) {
        return this.ratingRepository.save(rating);
    }

    @Override
    public Optional<Rating> getRatingById(Long id)  {
        return this.ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getRatingByUserId(Long userId) throws Exception {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(Long hotelId) throws Exception {
        return ratingRepository.findByHotelId(hotelId);
    }


    @Override
    public Rating updateRatingById(Long id, Rating updateRating) throws Exception {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        if (ratingOptional.isPresent()) {
            Rating existingRating = ratingOptional.get();

            if (updateRating.getFeedback() != null) {
                existingRating.setFeedback(updateRating.getFeedback());
            }
            if (updateRating.getRating() != existingRating.getRating()) {
                existingRating.setRating(updateRating.getRating());
            }
            // Save the updated course
            return ratingRepository.save(existingRating);
        } else {
            throw new Exception("Rating not found with id: " + id);
        }
    }

    @Override
    public void deleteRatingById(Long id) {
        this.ratingRepository.deleteById(id);
    }
}
