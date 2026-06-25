package com.vic.vagando.infrastructure.auth.controller;

import com.vic.vagando.app.domain.user.Register;
import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.interactor.UserInteractor;
import com.vic.vagando.infrastructure.auth.dto.LoginDTO;
import com.vic.vagando.infrastructure.auth.dto.RegisterDTO;
import com.vic.vagando.infrastructure.auth.service.TokenService;
import com.vic.vagando.infrastructure.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserInteractor userInteractor;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user and return a JWT token", description = "Authenticates the user with the provided email and password, and returns a JWT token if successful")
    public Map<String, String> login(@RequestBody LoginDTO loginRequest) {
            var userEmailPassword = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            var authentication = authenticationManager.authenticate(userEmailPassword);
            var user = (UserEntity) Objects.requireNonNull(authentication.getPrincipal());
            var token = tokenService.genToken(user);
            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole().getValue());
            return response;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details and returns a success message if successful")
    public String register(@RequestBody Register registerRequest) {
            String passwordHash = passwordEncoder.encode(registerRequest.getUser().getPassword());
            registerRequest.getUser().setPassword(passwordHash);
            userInteractor.registerUser(registerRequest);
            return "User registered successfully";
    }
}
