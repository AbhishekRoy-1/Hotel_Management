package com.roy.user.service.entities;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rating {
    private Long ratingId;
    private Long userId;
    private Long hotelId;
    private int rating;
    private String feedback;

    private Hotel hotel;
}
