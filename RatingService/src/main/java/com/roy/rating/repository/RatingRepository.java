package com.roy.rating.repository;

import com.roy.rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
   // @Query("SELECT r FROM Rating r WHERE r.userId = :userId")
    List<Rating> findByUserId(@Param("userId") Long userId);

   // @Query("SELECT r FROM Rating r WHERE r.hotelId = :hotelId")
    List<Rating> findByHotelId(@Param("hotelId") Long hotelId);
}
