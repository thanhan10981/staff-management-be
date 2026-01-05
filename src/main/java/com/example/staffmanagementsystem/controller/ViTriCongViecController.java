package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;
import com.example.staffmanagementsystem.service.ViTriCongViecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vi-tri-cong-viec")
@RequiredArgsConstructor
public class ViTriCongViecController {

    private final ViTriCongViecService service;

    @GetMapping
    public List<ViTriCongViec> getAll() {
        return service.findAll();
    }

    @GetMapping("/phongban/{maPhongBan}")
    public ResponseEntity<List<ViTriCongViecDTO>> getByPhongBan(
            @PathVariable Integer maPhongBan
    ) {
        List<ViTriCongViecDTO> result =
                service.getViTriTheoPhongBan(maPhongBan);

        return ResponseEntity.ok(result);
    }
}
