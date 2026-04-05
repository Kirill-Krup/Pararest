package com.actisys.userservice.service.impl;

import com.actisys.dto.CreateUserRequest;
import com.actisys.dto.UserAddDTO;
import com.actisys.userservice.mapper.UserMapper;
import com.actisys.userservice.model.User;
import com.actisys.userservice.model.UserRole;
import com.actisys.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_setsDefaultRoleWalletAndBlockedFlag() {
        UUID id = UUID.randomUUID();
        CreateUserRequest request = CreateUserRequest.builder()
                .id(id)
                .user(UserAddDTO.builder()
                        .login("user")
                        .email("u@test.com")
                        .fullName("User U")
                        .phone("+111")
                        .birthday(LocalDate.of(2001, 1, 1))
                        .build())
                .build();

        userService.register(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertEquals(UserRole.USER, saved.getRole());
        assertEquals(0, saved.getWallet().intValue());
        assertFalse(saved.getBlocked());
    }
}
