package com.actisys.authservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    private final String str;
    private final String password;
    private final Boolean rememberMe;
}
