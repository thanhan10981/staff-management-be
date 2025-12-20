package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.DashboardLuongDTO;
import com.example.staffmanagementsystem.dto.DashboardPhongBanDTO;
import com.example.staffmanagementsystem.dto.DashboardTrendDTO;
import com.example.staffmanagementsystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/phong-ban")
    public List<DashboardPhongBanDTO> phongBan() {
        return dashboardService.thongKeNhanVienTheoPhongBan();
    }

    @GetMapping("/trend")
    public List<DashboardTrendDTO> trend() {
        return dashboardService.thongKeNhanVienTheoThang();
    }
    @GetMapping("/luong")
    public List<DashboardLuongDTO> coCauLuong() {
        return dashboardService.coCauLuong();
    }
    @GetMapping("/nhan-vien")
    public ResponseEntity<Map<String, Object>> thongKeNhanVien() {
        return ResponseEntity.ok(dashboardService.thongKeNhanVien());
    }
    @GetMapping("/ti-le-nghi-phep")
    public ResponseEntity<?> tiLeNghiPhep() {
        return ResponseEntity.ok(dashboardService.thongKeTiLeNghiPhep());
    }


}
