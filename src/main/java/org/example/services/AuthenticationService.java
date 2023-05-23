package org.example.services;

import org.example.infrastructure.security.JwtRequest;

public interface AuthenticationService {

    String userAuthentication(JwtRequest authenticationRequest);

}
