package com.actisys.analyticsservice.client;

import reactor.core.publisher.Mono;

public interface OrderServiceClient {
    Mono<Long> getTotalCount();
}