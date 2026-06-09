package com.astrodiary.backend.controller;

import com.astrodiary.backend.entity.User; 
import com.astrodiary.backend.repository.UserRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // html i js komuniciraju sa ovim

public class UserController {

    @Autowired
    private UserRepository userRepository;

    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        
       
        if (foundUser.isEmpty()) {
            return ResponseEntity.status(404).body("You don't have an account, click REGISTER to create it.");
        }
        
        
        if (!foundUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(401).body("Password is incorrect.");
        }
        
       
        return ResponseEntity.ok(foundUser.get());
    }

    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        
        
        if (foundUser.isPresent()) {
            return ResponseEntity.status(409).body("This account already exists, click ENTER.");
        }
        
        
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(201).body(savedUser);

        }
    
}
