package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.PhongBanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/phongban")
@RequiredArgsConstructor
public class PhongBanController {

    private final PhongBanService service;

    @GetMapping
    public ResponseEntity<?> getAllPhongBan() {
        return ResponseEntity.ok(service.getAll());
    }
}
