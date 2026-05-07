package com.vic.vagando.infrastructure.auth.controller;

import com.vic.vagando.app.domain.user.Register;
import com.vic.vagando.app.domain.user.User;
import com.vic.vagando.app.interactor.UserInteractor;
import com.vic.vagando.infrastructure.auth.dto.LoginDTO;
import com.vic.vagando.infrastructure.auth.dto.RegisterDTO;
import com.vic.vagando.infrastructure.auth.service.TokenService;
import com.vic.vagando.infrastructure.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@CrossOrigin
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
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            var userEmailPassword = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            var authentication = authenticationManager.authenticate(userEmailPassword);

            var token = tokenService.genToken((UserEntity) Objects.requireNonNull(authentication.getPrincipal()));
            HashMap<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error logging in: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register registerRequest) {
        try {
            String passwordHash = passwordEncoder.encode(registerRequest.getUser().getPassword());
            registerRequest.getUser().setPassword(passwordHash);
            userInteractor.registerUser(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }
}
