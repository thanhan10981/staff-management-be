package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.entity.PhongBan;
import com.example.staffmanagementsystem.service.impl.PhongBanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/phongban")
@RequiredArgsConstructor
public class PhongBanController {

    private final PhongBanService phongBanService;

    @GetMapping("/khoa/{maKhoa}")
    public ResponseEntity<List<PhongBanDTO>> getPhongBanTheoKhoa(
            @PathVariable int maKhoa
    ) {
        return ResponseEntity.ok(phongBanService.getPhongBanTheoKhoa(maKhoa));
    }
    @GetMapping
    public List<PhongBan> getAllPhongBan() {
        return phongBanService.findAll();
    }
}
