package com.actisys.inventoryservice.controller;

import com.actisys.inventoryservice.dto.AddSupplyDTO;
import com.actisys.inventoryservice.dto.SupplyDTO;
import com.actisys.inventoryservice.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/supplies")
@RestController
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<SupplyDTO>> getAll() {
        return ResponseEntity.ok().body(supplyService.getAll());
    }

    @PostMapping("/addNew")
    public ResponseEntity<SupplyDTO> addNew(@RequestBody AddSupplyDTO supplyDTO) {
        return ResponseEntity.ok().body(supplyService.addNewSupply(supplyDTO));
    }

    @PutMapping("/editSupply/{id}")
    public ResponseEntity<SupplyDTO> editSupply(@PathVariable Long id, @RequestBody AddSupplyDTO supplyDTO) {
        return ResponseEntity.ok().body(supplyService.editSupply(id, supplyDTO));
    }

    @DeleteMapping("/deleteSupply/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
        return ResponseEntity.ok().build();
    }

}
