package com.actisys.authservice.service.impl;

import com.actisys.authservice.client.UserServiceClient;
import com.actisys.authservice.dto.RegisterRequest;
import com.actisys.authservice.model.Credential;
import com.actisys.authservice.repository.AuthRepository;
import com.actisys.authservice.util.JwtTokenProvider;
import com.actisys.dto.UserAddDTO;
import com.actisys.exception.UserServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthRepository authRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_whenUserServiceFails_rollsBackCredential() {
        RegisterRequest request = RegisterRequest.builder()
                .password("secret")
                .rememberMe(true)
                .newUser(UserAddDTO.builder()
                        .login("kiril")
                        .email("kiril@test.com")
                        .fullName("Kiril Test")
                        .phone("+123")
                        .birthday(LocalDate.of(2000, 1, 1))
                        .build())
                .build();

        when(authRepository.existsByLogin("kiril")).thenReturn(false);
        when(authRepository.existsByEmail("kiril@test.com")).thenReturn(false);
        when(passwordEncoder.encode("secret")).thenReturn("encoded");
        when(jwtTokenProvider.generate(any(), any())).thenReturn("access-token");
        when(jwtTokenProvider.generateRefreshToken(any(), any())).thenReturn("refresh-token");
        doThrow(new UserServiceException("userservice down")).when(userServiceClient).createUser(any());

        assertThrows(UserServiceException.class, () -> authService.register(request));

        ArgumentCaptor<Credential> captor = ArgumentCaptor.forClass(Credential.class);
        verify(authRepository).save(captor.capture());
        verify(authRepository).delete(any(Credential.class));
        assertTrue(captor.getValue().getRefreshToken().equals("refresh-token"));
    }
}
