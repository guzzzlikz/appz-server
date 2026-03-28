package org.example.appzserver.services;

import lombok.extern.slf4j.Slf4j;
import org.example.appzserver.components.HashComponent;
import org.example.appzserver.models.dtos.ApiResponseDTO;
import org.example.appzserver.models.dtos.UserDTO;
import org.example.appzserver.models.entities.User;
import org.example.appzserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HashComponent hashComponent;
    @Autowired
    private JWTService jwtService;

    public ResponseEntity<?> register(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            log.info("UserService register has been called but email is empty");
            return ResponseEntity.status(400).body("Email is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            log.info("UserService register has been called but password is empty");
            return ResponseEntity.status(400).body("Password is required");
        }
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            log.info("UserService register has been called but name is empty");
            return ResponseEntity.status(400).body("Name is required");
        }
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .xp(0)
                .lvl(1)
                .build();
        if (userRepository.existsByEmail(user.getEmail())) {
            log.info("UserService register has been called but email already exists");
            return ResponseEntity.status(400).body("Email already exists");
        }
        user.setPassword(hashComponent.hash(user.getPassword()));
        userRepository.save(user);
        user.setPassword(null);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                .token(jwtService.generateToken(user.getId()))
                .body(user)
                .build();
        return ResponseEntity.status(200).body(apiResponseDTO);
    }
    public ResponseEntity<?> login(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            log.info("UserService login has been called but email is empty");
            return ResponseEntity.status(400).body("Email is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            log.info("UserService login has been called but password is empty");
            return ResponseEntity.status(400).body("Password is required");
        }
        User user = User.builder()
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();
        if (!userRepository.existsByEmail(user.getEmail())) {
            log.info("UserService login has been called but email does not exist");
            return ResponseEntity.status(400).body("Email does not exist");
        }
        User mongoUser = userRepository.findByEmail(user.getEmail());
        if (hashComponent.hash(user.getPassword()).equals(mongoUser.getPassword())) {
            log.info("UserService login has been called and login is successful");
            mongoUser.setPassword(null);
            ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder()
                    .token(jwtService.generateToken(mongoUser.getId()))
                    .body(mongoUser)
                    .build();
            return ResponseEntity.status(200).body(apiResponseDTO);
        } else {
            log.info("UserService login has been called and passwords don't match");
            return ResponseEntity.status(400).body("Wrong password");
        }
    }
    public ResponseEntity<?> getUser(String token) {
        token = token.replace("Bearer ", "");
        String id = jwtService.getDataFromToken(token);
        Optional<User> user = userRepository.findById(id);
        user.get().setPassword(null);
        return ResponseEntity.status(200).body(user.get());
    }
}
