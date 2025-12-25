package com.example.staffmanagementsystem.controller;


import com.example.staffmanagementsystem.service.KhoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/khoa")
@RequiredArgsConstructor
public class KhoaController {

    private final KhoaService service;

    @GetMapping
    public ResponseEntity<?> getAllKhoa() {
        return ResponseEntity.ok(service.getAll());
    }
}
