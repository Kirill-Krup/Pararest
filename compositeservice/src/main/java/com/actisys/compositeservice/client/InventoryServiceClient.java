package com.actisys.compositeservice.client;

import com.actisys.compositeservice.dto.menu.AddRecipeDTO;
import reactor.core.publisher.Mono;

public interface InventoryServiceClient {
    Mono<Boolean> createRecipe(AddRecipeDTO recipeDTO);
    Mono<Void> deleteRecipeByDishId(Long dishId);
    Mono<AddRecipeDTO> getRecipeByDishId(Long dishId);
}