package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.PhongVatLyDTO;
import com.example.staffmanagementsystem.service.PhongVLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/phong")
@RequiredArgsConstructor
public class PhongVLController {

    private final PhongVLService phongService;

    @GetMapping("/khoa/{maKhoa}")
    public ResponseEntity<List<PhongVatLyDTO>> getPhongTheoKhoa(
            @PathVariable int maKhoa
    ) {
        return ResponseEntity.ok(phongService.getPhongTheoKhoa(maKhoa));
    }
}
