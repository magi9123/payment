package org.example.services.impl;

import org.example.infrastructure.security.JwtRequest;

public interface AuthenticationServiceImpl {

    String userAuthentication(JwtRequest authenticationRequest);

}
