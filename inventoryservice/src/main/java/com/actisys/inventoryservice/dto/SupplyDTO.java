package com.actisys.inventoryservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplyDTO {
    private Long id;
    private SupplierDTO supplier;
    private IngredientDTO ingredient;
    private LocalDateTime dateOfRequest;
    private LocalDateTime dateOfSupply;
}
