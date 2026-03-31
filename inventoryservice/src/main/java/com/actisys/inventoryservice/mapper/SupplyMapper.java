package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.SupplyDTO;
import com.actisys.inventoryservice.model.Supply;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {SupplierMapper.class, SupplyItemMapper.class})
public interface SupplyMapper {
    SupplyDTO toDTO(Supply supply);
    Supply toEntity(SupplyDTO supplyDTO);
}
