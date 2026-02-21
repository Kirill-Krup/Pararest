package com.actisys.inventoryservice.controller;

import com.actisys.inventoryservice.dto.AddRecipeDTO;
import com.actisys.inventoryservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/addNew")
    public ResponseEntity<Boolean> addNewRecipe(AddRecipeDTO addRecipeDTO) {
        return ResponseEntity.ok().body(recipeService.addNewRecipe(addRecipeDTO));
    }
}
