package com.saeed.fitnessWorkoutTracker.controller;

import com.saeed.fitnessWorkoutTracker.exception.ApiResponse;
import com.saeed.fitnessWorkoutTracker.model.User;
import com.saeed.fitnessWorkoutTracker.repository.UserRepository;
import com.saeed.fitnessWorkoutTracker.security.jwt.JwtUtils;
import com.saeed.fitnessWorkoutTracker.security.request.LoginRequest;
import com.saeed.fitnessWorkoutTracker.security.request.SignupRequest;
import com.saeed.fitnessWorkoutTracker.security.response.MessageResponse;
import com.saeed.fitnessWorkoutTracker.security.response.UserInfoResponse;
import com.saeed.fitnessWorkoutTracker.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<?>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Bad credentials");
            response.put("status", false);
            return new ResponseEntity<>(new ApiResponse<>("Access denied",HttpStatus.OK.value(), response), HttpStatus.UNAUTHORIZED);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        // Get roles
        String role = userDetails.getAuthorities().isEmpty() ? null : userDetails.getAuthorities().iterator().next().getAuthority();

        // Return a properly formatted response
        Map<String, Object> response = new HashMap<>();
        response.put("id", userDetails.getId());
        response.put("jwtToken", jwtToken);
        response.put("username", userDetails.getUsername());
        response.put("role", role);

        return new ResponseEntity<>(new ApiResponse<>(" Login Successfully ",HttpStatus.OK.value(), response), HttpStatus.OK);

    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Error: Username is already taken!",HttpStatus.OK.value(), HttpStatus.OK));

        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Error: Email is already in use!",HttpStatus.OK.value(), HttpStatus.OK));

        }
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.badRequest().body(new ApiResponse<>("User registered successfully!",HttpStatus.OK.value(), HttpStatus.OK));


    }
    @GetMapping("/username")
    public String currecntUserName(Authentication authentication) {
        if(authentication != null) {
            return authentication.getName();
        } else {
            return " ";
        }
    }




}