package com.medical.api.controllers;

import com.medical.api.dto.AuthRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest authRequest) {
        var token =  new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password());
        var authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok("Bearer " + authentication.getCredentials());
    }
}
