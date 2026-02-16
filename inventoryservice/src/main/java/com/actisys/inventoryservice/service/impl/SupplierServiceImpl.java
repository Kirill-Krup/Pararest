package com.actisys.inventoryservice.service.impl;

import com.actisys.inventoryservice.dto.AddSupplierDTO;
import com.actisys.inventoryservice.dto.SupplierDTO;
import com.actisys.inventoryservice.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Override
    public List<SupplierDTO> getAll() {
        return List.of();
    }

    @Override
    public SupplierDTO addNewSupplier(AddSupplierDTO supplierDTO) {
        return null;
    }

    @Override
    public SupplierDTO editSupplier(Long id, AddSupplierDTO supplierDTO) {
        return null;
    }

    @Override
    public void deleteSupplier(Long id) {

    }
}
