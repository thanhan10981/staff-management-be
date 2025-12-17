package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.staffmanagementsystem.service.impl.ViTriCongViecService;

@RestController
@RequestMapping("/api/vitri")
@RequiredArgsConstructor
public class ViTriCongViecController {

    private final ViTriCongViecService viTriCongViecService;

    @GetMapping
    public ResponseEntity<List<ViTriCongViecDTO>> getAll() {
        return ResponseEntity.ok(viTriCongViecService.getAll());
    }
}
