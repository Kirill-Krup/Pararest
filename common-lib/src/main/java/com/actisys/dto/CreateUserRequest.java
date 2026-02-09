package com.actisys.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    private final UUID id;
    private final UserDTO user;
}
