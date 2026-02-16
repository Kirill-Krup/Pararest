package com.actisys.inventoryservice.service.impl;

import com.actisys.inventoryservice.dto.AddSupplyDTO;
import com.actisys.inventoryservice.dto.SupplyDTO;
import com.actisys.inventoryservice.service.SupplyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {
    @Override
    public List<SupplyDTO> getAll() {
        return List.of();
    }

    @Override
    public SupplyDTO addNewSupply(AddSupplyDTO supplyDTO) {
        return null;
    }

    @Override
    public SupplyDTO editSupply(Long id, AddSupplyDTO supplyDTO) {
        return null;
    }

    @Override
    public void deleteSupply(Long id) {

    }
}
