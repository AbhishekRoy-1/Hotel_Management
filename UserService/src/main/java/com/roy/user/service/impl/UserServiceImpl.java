package com.roy.user.service.impl;

import com.roy.user.service.entities.Hotel;
import com.roy.user.service.entities.Rating;
import com.roy.user.service.entities.User;
import com.roy.user.service.exception.ResourceNotFoundException;
import com.roy.user.service.repository.UserRepository;
import com.roy.user.service.services.UserService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
            logger.info("{}", ratingsOfUser);
            List<Rating> ratingList = Arrays.stream(ratingsOfUser)
                    .map(rating -> {
                        ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                        Hotel hotel = forEntity.getBody();
                        logger.info("get Status code: {}",forEntity.getStatusCode());
                        rating.setHotel(hotel);
                        return rating;
                    })
                    .collect(Collectors.toList());

            user.setRatings(ratingList);
        }

        return users;
    }
    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    //Using RestTemplate to get data from a different micro-service
    @Override
    public Optional<User> getByUserId(Long id)  {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: "+id+" not found!"));
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/user/"+user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUser);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream()
                .map(rating -> {
                    ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
                    Hotel hotel = forEntity.getBody();
                    logger.info("get Status code: {}",forEntity.getStatusCode());
                    rating.setHotel(hotel);
                    return rating;
                })
                .collect(Collectors.toList());

        user.setRatings(ratingList);
        return Optional.ofNullable(user);
    }
    @Override
    public User updateUserById(Long id, User updateUser) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            if (updateUser.getName() != null) {
                existingUser.setName(updateUser.getName());
            }
            if (updateUser.getAbout() != null) {
                existingUser.setAbout(updateUser.getAbout());
            }
            if (updateUser.getEmail() != null) {
                existingUser.setEmail(updateUser.getEmail());
            }
            if (updateUser.getDob() != null) {
                existingUser.setDob(updateUser.getDob());
            }
            if (updateUser.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(updateUser.getPhoneNumber());
            }

            // Save the updated course
            return userRepository.save(existingUser);
        } else {
            throw new Exception("User not found with id: " + id);
        }
    }
 
    @Override
    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }
}
