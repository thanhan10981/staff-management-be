package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.ViTriCongViecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vitri")
@RequiredArgsConstructor
public class ViTriCongViecController {

    private final ViTriCongViecService service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
