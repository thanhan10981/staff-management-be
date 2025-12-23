package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/quy-luong-phong-ban")
    public List<QuyLuongPhongBanDto> getQuyLuongTheoPhongBan(
            @RequestParam String period,
            @RequestParam(required = false) Integer department,
            @RequestParam(required = false) String role
    ) {
        return dashboardService.getQuyLuongTheoPhongBan(period, department, role);
    }


    @GetMapping("/luong-bao-cao")
    public List<DashboardLuongDTO> coCauLuong(
            @RequestParam String period,
            @RequestParam(required = false) Integer department
    ) {
        return dashboardService.coCauLuongbaocao(period, department);
    }
    @GetMapping("/luong/bang-nhan-vien")
    public List<BangLuongNhanVienDTO> getBangLuongNhanVien(
            @RequestParam String period,
            @RequestParam(required = false) Integer department,
            @RequestParam(required = false) String role
    ) {
        return dashboardService.getBangLuongNhanVien(period, department, role);
    }

    @GetMapping("/luong/kpi")
    public KpiLuongDTO getKpiLuong(
            @RequestParam String period,
            @RequestParam(required = false) Integer department,
            @RequestParam(required = false) String role
    ) {
        return dashboardService.getKpiLuongBaoCao(period, department, role);
    }

    @GetMapping("/quy-luong-thang")
    public QuyLuongThangDTO getQuyLuongThang() {
        return dashboardService.getQuyLuongThang();
    }

}
