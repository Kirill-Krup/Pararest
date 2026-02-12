package com.actisys.userservice.controller;

import com.actisys.dto.CreateUserRequest;
import com.actisys.userservice.dto.UserDTO;
import com.actisys.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/createNewUser")
    public ResponseEntity<Void> register(@RequestBody CreateUserRequest createUserRequest){
        userService.register(createUserRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getInfo")
    public ResponseEntity<UserDTO> getInfo(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok().body(userService.getInfo(UUID.fromString(userId)));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.ok().body(userService.getAll());
    }

}
