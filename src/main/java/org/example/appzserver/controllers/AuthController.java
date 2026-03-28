package org.example.appzserver.controllers;


import org.example.appzserver.models.dtos.UserDTO;
import org.example.appzserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return authService.register(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        return authService.login(userDTO);
    }
    @GetMapping("user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return authService.getUser(token);
    }
}
