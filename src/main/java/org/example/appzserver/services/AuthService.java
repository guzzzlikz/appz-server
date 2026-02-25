package org.example.appzserver.services;

import lombok.extern.slf4j.Slf4j;
import org.example.appzserver.components.HashComponent;
import org.example.appzserver.models.User;
import org.example.appzserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashComponent hashComponent;

    public ResponseEntity<?> register(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.info("UserService register has been called but email is empty");
            return ResponseEntity.status(400).body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            log.info("UserService register has been called but password is empty");
            return ResponseEntity.status(400).body("Password is required");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            log.info("UserService register has been called but name is empty");
            return ResponseEntity.status(400).body("Name is required");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            log.info("UserService register has been called but email already exists");
            return ResponseEntity.status(400).body("Email already exists");
        }
        user.setPassword(hashComponent.hash(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(200).body("User registered successfully");
    }
    public ResponseEntity<?> login(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            log.info("UserService login has been called but email is empty");
            return ResponseEntity.status(400).body("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            log.info("UserService login has been called but password is empty");
            return ResponseEntity.status(400).body("Password is required");
        }
        if (!userRepository.existsByEmail(user.getEmail())) {
            log.info("UserService login has been called but email does not exist");
            return ResponseEntity.status(400).body("Email does not exist");
        }
        User mongoUser = userRepository.findByEmail(user.getEmail());
        if (hashComponent.hash(user.getPassword()).equals(mongoUser.getPassword())) {
            log.info("UserService login has been called and login is successful");
            return ResponseEntity.status(200).body("User logged in successfully");
        } else {
            log.info("UserService login has been called and passwords don't match");
            return ResponseEntity.status(400).body("Wrong password");
        }
    }
}
