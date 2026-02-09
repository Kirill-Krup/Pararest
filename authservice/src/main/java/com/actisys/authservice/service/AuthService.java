package com.actisys.authservice.service;


import com.actisys.authservice.dto.JwtResponse;
import com.actisys.authservice.dto.LoginRequest;
import com.actisys.authservice.dto.RegisterRequest;
import org.jspecify.annotations.Nullable;

public interface AuthService {
    JwtResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);
}
