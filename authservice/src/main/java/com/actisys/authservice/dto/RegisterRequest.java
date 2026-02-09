package com.actisys.authservice.dto;

import com.actisys.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class RegisterRequest {
    private String password;
    private boolean rememberMe;
    private UserDTO newUser;
}
