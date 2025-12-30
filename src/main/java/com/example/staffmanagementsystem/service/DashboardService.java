package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<DashboardPhongBanDTO> thongKeNhanVienTheoPhongBan();
    List<DashboardTrendDTO> thongKeNhanVienTheoThang();
    List<DashboardLuongDTO> coCauLuong();
    Map<String, Object> thongKeNhanVien();
    Map<String, Object> thongKeTiLeNghiPhep();
    List<QuyLuongPhongBanDto> getQuyLuongTheoPhongBan(String period, Integer dept,  String role);
    List<DashboardLuongDTO> coCauLuongbaocao(String period, Integer dept);
    List<BangLuongNhanVienDTO> getBangLuongNhanVien(
            String period,
            Integer dept,
            String role
    );
    KpiLuongDTO getKpiLuongBaoCao(
            String period,
            Integer dept,
            String role
    );
    QuyLuongThangDTO getQuyLuongThang(
    );


}

