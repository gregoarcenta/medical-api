package com.medical.api.controllers;

import com.medical.api.dto.AuthRequest;
import com.medical.api.dto.AuthResponse;
import com.medical.api.models.User;
import com.medical.api.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/login") class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        var AuthenticationToken = new UsernamePasswordAuthenticationToken(authRequest.username(),
                                                                          authRequest.password()
        );
        var authentication = authenticationManager.authenticate(AuthenticationToken);
        User user = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(new AuthResponse.AuthResponseUser(user.getId(), user.getUsername()),
                                                  tokenJWT
        ));
    }
}
