package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.SupplyItemDTO;
import com.actisys.inventoryservice.model.SupplyItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SupplyItemMapper {
    SupplyItemDTO toDTO(SupplyItem supplyItem);
}
