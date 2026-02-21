package com.actisys.inventoryservice.service;

import com.actisys.inventoryservice.dto.AddRecipeDTO;

import java.util.HashMap;

public interface RecipeService {
    Boolean addNewRecipe(AddRecipeDTO addRecipeDTO);
}
