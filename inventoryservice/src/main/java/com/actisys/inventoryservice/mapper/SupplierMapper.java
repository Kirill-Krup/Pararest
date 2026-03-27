package com.actisys.inventoryservice.mapper;

import com.actisys.inventoryservice.dto.SupplierDTO;
import com.actisys.inventoryservice.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDTO toDTO(Supplier supplier);
    Supplier toEntity(SupplierDTO supplierDTO);
}
