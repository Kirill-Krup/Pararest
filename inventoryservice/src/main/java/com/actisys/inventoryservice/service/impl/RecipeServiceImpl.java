package com.actisys.inventoryservice.service.impl;

import com.actisys.inventoryservice.dto.AddRecipeDTO;
import com.actisys.inventoryservice.exception.RecipeNotFoundException;
import com.actisys.inventoryservice.model.Recipe;
import com.actisys.inventoryservice.model.RecipeId;
import com.actisys.inventoryservice.repository.IngredientRepository;
import com.actisys.inventoryservice.repository.RecipeRepository;
import com.actisys.inventoryservice.service.RecipeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional
    public Boolean addNewRecipe(AddRecipeDTO addRecipeDTO) {
        for(Map.Entry<Long, Integer>  item: addRecipeDTO.getIngredientsMap().entrySet()) {
            Recipe recipe = Recipe.builder()
                    .id(new RecipeId(addRecipeDTO.getMenuItemId(), item.getKey()))
                    .ingredient(ingredientRepository.findById(item.getKey()).orElseThrow(()->new RecipeNotFoundException(item.getKey())))
                    .quantity(item.getValue())
                    .build();
            recipeRepository.save(recipe);
        }
        return null;
    }
}
