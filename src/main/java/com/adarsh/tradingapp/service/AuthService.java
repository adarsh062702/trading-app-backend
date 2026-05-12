package com.adarsh.tradingapp.service;

import com.adarsh.tradingapp.dto.LoginRequest;
import com.adarsh.tradingapp.dto.LoginResponse;
import com.adarsh.tradingapp.dto.RegisterRequest;
import com.adarsh.tradingapp.entity.Role;
import com.adarsh.tradingapp.entity.User;
import com.adarsh.tradingapp.entity.wallet;
import com.adarsh.tradingapp.repository.UserRepository;
import com.adarsh.tradingapp.repository.WalletRepository;
import com.adarsh.tradingapp.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        wallet userWallet = new wallet();

        userWallet.setBalance(BigDecimal.ZERO);
        userWallet.setUser(savedUser);

        walletRepository.save(userWallet);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}