package com.roy.user.service.services;

import com.roy.user.service.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> getByUserId(Long id) throws Exception;
    User updateUserById(Long id, User updateUser) throws Exception;
    void deleteUserById(Long id);
}
