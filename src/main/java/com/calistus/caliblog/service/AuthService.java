package com.calistus.caliblog.service;

import com.calistus.caliblog.data.model.Role;
import com.calistus.caliblog.data.model.User;
import com.calistus.caliblog.dto.request.LoginRequest;
import com.calistus.caliblog.dto.request.RegisterRequest;
import com.calistus.caliblog.dto.response.AuthResponse;
import com.calistus.caliblog.repository.UserRepository;
import com.calistus.caliblog.security.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }


        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.ADMIN));
        user.setCreatedAt(Instant.now());

        userRepository.save(user);
    }


    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        String token = jwtService.generateToken(request.getUsername());
        return new AuthResponse(token);
    }





}
