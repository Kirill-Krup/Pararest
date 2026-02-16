package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.SupplyDTO;
import com.actisys.inventoryservice.model.Supply;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SupplierMapper.class, IngredientMapper.class})
public interface SupplyMapper {
    SupplyDTO toDTO(Supply supply);
}
