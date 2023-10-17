package com.roy.hotel.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
