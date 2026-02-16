package com.actisys.inventoryservice.service;

import com.actisys.inventoryservice.dto.AddSupplyDTO;
import com.actisys.inventoryservice.dto.SupplyDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface SupplyService {
    List<SupplyDTO> getAll();

    SupplyDTO addNewSupply(AddSupplyDTO supplyDTO);

    SupplyDTO editSupply(Long id, AddSupplyDTO supplyDTO);

    void deleteSupply(Long id);
}
