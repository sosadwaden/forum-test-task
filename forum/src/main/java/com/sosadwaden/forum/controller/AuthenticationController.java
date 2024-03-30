package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.AuthenticationRequest;
import com.sosadwaden.forum.api.request.RegisterRequest;
import com.sosadwaden.forum.api.response.AuthenticationResponse;
import com.sosadwaden.forum.service.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication controller")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register",
            description = "Register a new user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration was successful"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(
            summary = "Authenticate",
            description = "User authentication"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication was successful"),
            @ApiResponse(responseCode = "401", description = "Bad credentials"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
