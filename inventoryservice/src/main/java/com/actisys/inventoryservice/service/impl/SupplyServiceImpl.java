package com.actisys.inventoryservice.service.impl;

import com.actisys.inventoryservice.dto.AddSupplyDTO;
import com.actisys.inventoryservice.dto.SupplyDTO;
import com.actisys.inventoryservice.exception.SupplyNotFoundException;
import com.actisys.inventoryservice.mapper.SupplyMapper;
import com.actisys.inventoryservice.model.Supply;
import com.actisys.inventoryservice.repository.SupplyRepository;
import com.actisys.inventoryservice.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {

    private final SupplyRepository supplyRepository;
    private final SupplyMapper supplyMapper;

    @Override
    public List<SupplyDTO> getAll() {
        List<Supply> list =supplyRepository.findAll() ;
        return list.stream().map(supplyMapper::toDTO).collect(Collectors.toList());
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
        Supply supply = supplyRepository.findById(id).orElseThrow(()->new SupplyNotFoundException(id));
        supplyRepository.delete(supply);
    }
}
