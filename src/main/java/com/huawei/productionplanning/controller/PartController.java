package com.huawei.productionplanning.controller;

import com.huawei.productionplanning.dto.PartDTO;
import com.huawei.productionplanning.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parts")
@RequiredArgsConstructor
public class PartController {
    private final PartService partService;
    @PostMapping
    public ResponseEntity<?> createModel(@RequestBody PartDTO partDTO) {
        partService.createPart(partDTO);
        return ResponseEntity.ok().build();
    }

}
