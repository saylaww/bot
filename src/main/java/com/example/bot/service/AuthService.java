package com.example.bot.service;

import com.example.bot.entity.User;
import com.example.bot.payload.ApiResponse;
import com.example.bot.payload.LoginDto;
import com.example.bot.payload.ResDto;
import com.example.bot.repository.UserRepository;
import com.example.bot.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            ResDto resDto = new ResDto(user.getRoles(),token);
            return new ApiResponse("TOken", true, resDto);
        } catch (Exception e) {
            return new ApiResponse("Parol yamasa login qa`te", false);
        }
    }

}
