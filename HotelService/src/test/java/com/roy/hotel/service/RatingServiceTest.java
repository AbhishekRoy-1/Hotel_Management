package com.roy.hotel.service;

import com.roy.hotel.service.entities.Rating;
import com.roy.hotel.service.serviceImpl.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class RatingServiceTest {
    final Logger logger = LoggerFactory.getLogger(RatingServiceTest.class);

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private HotelServiceImpl hotelServiceImpl;

    // Initialize mocks
    public RatingServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRating() {
        // Create a mock Rating object
        Rating mockRating = new Rating();
        mockRating.setRatingId(1L);
        mockRating.setUserId(123L);
        mockRating.setHotelId(456L);
        mockRating.setRating(10);
        mockRating.setFeedback("Great experience!");

        // Configure the mock behavior
        when(ratingService.getRating(anyLong())).thenReturn(mockRating);
//        logger.info();
//
        // Call the method in your class that uses the RatingService
        // and verify the expected behavior based on the mock response
        // ...
    }
}