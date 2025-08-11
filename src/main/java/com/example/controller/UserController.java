package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.dto.UpdateUserDto;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.CustomUserDetailsService;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users") 
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private CustomUserDetailsService userDetailsService;
    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        userService.registerUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile(Principal principal) {
        return ResponseEntity.ok(userRepository.findByUsername(principal.getName()).orElseThrow());
    }

    @PatchMapping("/me")
    public ResponseEntity<User> updateMyProfile(Principal principal, @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = userService.updateUser(principal.getName(), updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/me/history")
    public ResponseEntity<?> getMyGameHistory(Principal principal) {
        return ResponseEntity.ok(userService.getGameHistory(principal.getName()));
    }
}