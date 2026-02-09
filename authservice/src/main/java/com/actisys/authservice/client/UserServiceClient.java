package com.actisys.authservice.client;


import com.actisys.dto.CreateUserRequest;
import com.actisys.dto.UserDTO;
import com.actisys.exception.UserServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient webClient;

    @Value("${services.user-service.url}")
    private String userServiceUrl;

    @Value("${services.user-service.create-user.url}")
    private String createUserUrl;

    public void createUser(CreateUserRequest user) {
        try {
            webClient.post()
                    .uri(userServiceUrl + createUserUrl)
                    .bodyValue(user)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(errorBody -> new UserServiceException("UserService error: " + errorBody))
                    )
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();

            log.info("User profile created successfully for userId: {}", user.getId());

        } catch (WebClientResponseException e) {
            log.error("UserService returned error for userId: {}, status: {}, body: {}",
                    user.getId(), e.getStatusCode(), e.getResponseBodyAsString());
            throw new UserServiceException("Failed to create user in UserService: " + e.getMessage(), e);

        } catch (Exception e) {
            log.error("Failed to call UserService for userId: {}", user.getId(), e);
            throw new UserServiceException("UserService is unavailable: " + e.getMessage(), e);
        }
    }
}
