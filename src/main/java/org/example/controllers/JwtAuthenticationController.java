package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.infrastructure.security.JwtRequest;
import org.example.infrastructure.security.JwtResponse;
import org.example.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    public static final String AUTHENTICATE = "/authenticate";

    private final AuthenticationService authenticationService;

    @PostMapping(value = AUTHENTICATE)
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return ResponseEntity.ok(new JwtResponse(authenticationService.userAuthentication(authenticationRequest)));
    }
}
