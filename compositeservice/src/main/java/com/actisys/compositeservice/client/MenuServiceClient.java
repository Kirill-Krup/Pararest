package com.actisys.compositeservice.client;

import com.actisys.compositeservice.dto.menu.AddMenuItem;
import com.actisys.compositeservice.dto.menu.AddMenuItemDTO;
import reactor.core.publisher.Mono;

public interface MenuServiceClient {
    Mono<Long> createDish(AddMenuItemDTO dishDto);

    Mono<Void> deleteDish(Long dishId);

    Mono<AddMenuItem> getDishById(Long dishId);
}
