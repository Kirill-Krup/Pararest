package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.IngredientDTO;
import com.actisys.inventoryservice.model.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDTO toDto(Ingredient ingredient);
}
