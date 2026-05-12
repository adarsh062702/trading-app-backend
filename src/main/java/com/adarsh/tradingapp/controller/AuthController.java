package com.adarsh.tradingapp.controller;

import com.adarsh.tradingapp.dto.LoginRequest;
import com.adarsh.tradingapp.dto.LoginResponse;
import com.adarsh.tradingapp.dto.RegisterRequest;
import com.adarsh.tradingapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request
    ) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {

        return authService.login(request);
    }
}