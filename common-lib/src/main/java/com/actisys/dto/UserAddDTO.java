package com.actisys.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class UserDTO {
    private String login;
    private String email;
    private String fullName;
    private String phone;
    private LocalDate birthday;
}
