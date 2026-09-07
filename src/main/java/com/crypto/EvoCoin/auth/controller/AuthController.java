package com.crypto.EvoCoin.auth.controller;

import com.crypto.EvoCoin.auth.model.LoginRequest;
import com.crypto.EvoCoin.auth.model.LoginResponse;
import com.crypto.EvoCoin.auth.service.AuthService;
import com.crypto.EvoCoin.user.model.UserRegistration;
import com.crypto.EvoCoin.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping(value = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody LoginRequest request){
        try {
            return authService.login(request);
        } catch (Exception e) {
            log.error("Failed: login", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping(value = "/api/auth/saveNewUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveNewUser(@RequestBody UserRegistration pUser){
        try {
            userService.saveUser(pUser);
        } catch (Exception e) {
            log.error("Failed: saveNewUser", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
