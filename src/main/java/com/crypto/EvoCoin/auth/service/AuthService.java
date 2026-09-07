package com.crypto.EvoCoin.auth.service;


import com.crypto.EvoCoin.auth.model.LoginRequest;
import com.crypto.EvoCoin.auth.model.LoginResponse;
import com.crypto.EvoCoin.user.entity.User;
import com.crypto.EvoCoin.user.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest pLoginRequest) {
        User vUser = userService.findUserByUsername(pLoginRequest.username());

        if(vUser == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!(passwordEncoder.matches(pLoginRequest.password(), vUser.getPassword()))){
            throw new BadCredentialsException("Invalid username or password");
        }

        String token = jwtService.generateToken(vUser);

        return new LoginResponse(token);
    }
}
