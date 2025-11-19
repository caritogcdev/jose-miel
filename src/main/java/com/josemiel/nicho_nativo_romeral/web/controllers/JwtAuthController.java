package com.josemiel.nicho_nativo_romeral.web.controllers;

import com.josemiel.nicho_nativo_romeral.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JwtAuthController {
    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtService jwtService;

    /** DTO para la solicitud de inicio de sesión. */
    public record LoginRequest(String email, String password) {
    }

    /** DTO para la respuesta de inicio de sesión. */
    public record LoginResponse(String token) {
    }

    /**
     * Maneja la solicitud de inicio de sesión.
     * 
     * @param req
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        var authToken = new UsernamePasswordAuthenticationToken(req.email(), req.password());
        authManager.authenticate(authToken); // lanza excepción si falla
        UserDetails user = uds.loadUserByUsername(req.email());
        String token = jwtService.generateToken(user, java.util.Map.of(
                "role", user.getAuthorities().stream().findFirst().map(Object::toString).orElse("ROLE_USER")));
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
