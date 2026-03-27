package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.SupplyItemDTO;
import com.actisys.inventoryservice.model.SupplyItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplyItemMapper {
    SupplyItemDTO toDTO(SupplyItem supplyItem);
}
