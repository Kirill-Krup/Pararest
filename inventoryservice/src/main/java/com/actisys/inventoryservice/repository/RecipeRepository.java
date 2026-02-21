package com.actisys.inventoryservice.repository;

import com.actisys.inventoryservice.model.Recipe;
import com.actisys.inventoryservice.model.RecipeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, RecipeId> {
}
