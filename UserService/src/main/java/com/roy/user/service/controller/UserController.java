package com.roy.user.service.controller;

import com.roy.user.service.entities.User;
import com.roy.user.service.exception.ResourceNotFoundException;
import com.roy.user.service.repository.UserRepository;
import com.roy.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User user1 = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getSingleUser(@PathVariable Long userId) throws Exception {
      Optional<User> user = userService.getByUserId(userId);
      if(user.isEmpty()){
          throw new ResourceNotFoundException("User not found " + userId);
      }
      return ResponseEntity.ok(user);
    }
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUser()  {
        List<User> allUser = userService.getAllUsers();

        return ResponseEntity.ok(allUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            this.userService.deleteUserById(userId);
            return ResponseEntity.ok("User with ID " + userId + " has been successfully deleted.");

        }
        else return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser (@PathVariable("userId") Long userId, @RequestBody User updatedUser) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User updated = this.userService.updateUserById(userId, updatedUser);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
