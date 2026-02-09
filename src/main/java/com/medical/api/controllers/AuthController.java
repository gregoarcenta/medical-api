package com.medical.api.controllers;

import com.medical.api.dto.AuthRequest;
import com.medical.api.dto.AuthResponse;
import com.medical.api.infra.errors.ApiErrorResponse;
import com.medical.api.models.User;
import com.medical.api.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticación", description = "Endpoint para obtener el token JWT necesario para las demás operaciones")
class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario y devuelve un token JWT válido por 2 horas."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Credenciales inválidas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
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
