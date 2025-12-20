package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.DashboardLuongDTO;
import com.example.staffmanagementsystem.dto.DashboardPhongBanDTO;
import com.example.staffmanagementsystem.dto.DashboardTrendDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<DashboardPhongBanDTO> thongKeNhanVienTheoPhongBan();
    List<DashboardTrendDTO> thongKeNhanVienTheoThang();
    List<DashboardLuongDTO> coCauLuong();
    Map<String, Object> thongKeNhanVien();
    Map<String, Object> thongKeTiLeNghiPhep();
}

