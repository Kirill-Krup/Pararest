package com.actisys.compositeservice.client.impl;

import com.actisys.compositeservice.client.MenuServiceClient;
import com.actisys.compositeservice.config.ServiceProperties.MenuServiceProperties;
import com.actisys.compositeservice.dto.menu.AddMenuItem;
import com.actisys.compositeservice.dto.menu.AddMenuItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j
public class MenuServiceClientImpl implements MenuServiceClient {

    private final WebClient webClient;
    private final MenuServiceProperties menuProperties;

    public MenuServiceClientImpl(WebClient.Builder webClientBuilder, MenuServiceProperties menuProperties) {
        this.menuProperties = menuProperties;
        this.webClient = webClientBuilder
                .baseUrl(menuProperties.getHost())
                .build();
    }

    @Override
    public Mono<Long> createDish(AddMenuItemDTO dishDto) {
        String url = menuProperties.getHost() + menuProperties.getEndpoints().getAddNew();

        log.debug("Creating dish in menu service: {}", url);

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dishDto)
                .retrieve()
                .bodyToMono(Long.class)
                .timeout(Duration.ofSeconds(10))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .doOnSuccess(success -> {
                    if (success != null) {
                        log.info("Dish created successfully");
                    } else {
                        log.warn("Dish creation returned false");
                    }
                })
                .doOnError(e -> log.error("Failed to create dish: {}", e.getMessage()));
    }

    @Override
    public Mono<Void> deleteDish(Long dishId) {
        String url = menuProperties.getHost() + menuProperties.getEndpoints().getDeleteDishById() + dishId;
        log.debug("Deleting dish with ID: {} from menu service", dishId);
        return webClient.delete()
                .uri(url)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(5))
                .doOnSuccess(v -> log.info("Dish {} deleted successfully", dishId))
                .onErrorResume(e -> {
                    log.error("Failed to delete dish {}: {}", dishId, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<AddMenuItem> getDishById(Long dishId) {
        String url = menuProperties.getHost() + menuProperties.getEndpoints().getGetDishById() + dishId;
        log.debug("Getting dish with ID: {} from menu service", dishId);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(AddMenuItem.class)
                .timeout(Duration.ofSeconds(5))
                .doOnSuccess(dish -> log.info("Dish {} retrieved successfully", dishId))
                .doOnError(e -> log.error("Failed to get dish {}: {}", dishId, e.getMessage()));
    }
}