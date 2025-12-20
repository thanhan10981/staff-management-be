package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.DashboardLuongDTO;
import com.example.staffmanagementsystem.dto.DashboardPhongBanDTO;
import com.example.staffmanagementsystem.dto.DashboardTrendDTO;
import com.example.staffmanagementsystem.repository.DonXinNghiRepository;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final LuongThangRepository luongThangRepo;
    private final NhanVienRepository nhanVienRepo;
    private final DonXinNghiRepository donXinNghiRepository;

    @Override
    public List<DashboardPhongBanDTO> thongKeNhanVienTheoPhongBan() {
        return nhanVienRepo.countNhanVienByPhongBan()
                .stream()
                .map(r -> new DashboardPhongBanDTO(
                        (String) r[0],
                        (Long) r[1]
                ))
                .toList();
    }

    @Override
    public List<DashboardTrendDTO> thongKeNhanVienTheoThang() {
        return nhanVienRepo.countNhanVienByMonth()
                .stream()
                .map(r -> new DashboardTrendDTO(
                        (Integer) r[0],
                        (Long) r[1]
                ))
                .toList();
    }


    @Override
    public List<DashboardLuongDTO> coCauLuong() {

        List<Object[]> latestList = luongThangRepo.findLatestMonthYear();

        // KHÔNG CÓ DỮ LIỆU
        if (latestList == null || latestList.isEmpty()) {
            return List.of();
        }

        Object[] latest = latestList.get(0);

        int thang = ((Number) latest[0]).intValue();
        int nam   = ((Number) latest[1]).intValue();

        List<Object[]> list = luongThangRepo.tongHopLuongTheoThang(thang, nam);

        if (list.isEmpty()) return List.of();

        Object[] r = list.get(0);

        double luongCoBan = ((Number) r[0]).doubleValue();
        double phuCap     = ((Number) r[1]).doubleValue();
        double bhxh       = ((Number) r[2]).doubleValue();
        double thue       = ((Number) r[3]).doubleValue();


        if (r == null || r.length < 4) {
            return List.of();
        }

        return List.of(
                new DashboardLuongDTO("Lương cơ bản", ((Number) r[0]).doubleValue()),
                new DashboardLuongDTO("Phụ cấp",      ((Number) r[1]).doubleValue()),
                new DashboardLuongDTO("BHXH",          ((Number) r[2]).doubleValue()),
                new DashboardLuongDTO("Thuế TNCN",     ((Number) r[3]).doubleValue())
        );
    }
    @Override
    public Map<String, Object> thongKeNhanVien() {

        Map<String, Object> result = new HashMap<>();

        // 1️⃣ LẤY THÁNG / NĂM MỚI NHẤT
        List<Object[]> latestList = luongThangRepo.findLatestMonthYear();

        if (latestList == null || latestList.isEmpty()) {
            result.put("tong", 0L);
            result.put("chenhLech", 0L);
            return result;
        }

        Object[] latest = latestList.get(0);

        int thang = ((Number) latest[0]).intValue();
        int nam   = ((Number) latest[1]).intValue();

        int prevMonth = (thang == 1) ? 12 : thang - 1;
        int prevYear  = (thang == 1) ? nam - 1 : nam;

        long current  = nhanVienRepo.countNhanVienDenThang(thang, nam);
        long previous = nhanVienRepo.countNhanVienDenThang(prevMonth, prevYear);

        result.put("tong", current);
        result.put("chenhLech", current - previous);

        return result;
    }
    @Override
    public Map<String, Object> thongKeTiLeNghiPhep() {

        Map<String, Object> result = new HashMap<>();

        Object raw = donXinNghiRepository.findLatestMonthYear();
        if (raw == null) {
            result.put("tiLe", 0);
            result.put("chenhLech", 0);
            return result;
        }

        Object[] latest = (Object[]) raw;

        int thang = ((Number) latest[0]).intValue();
        int nam   = ((Number) latest[1]).intValue();

        int prevMonth = (thang == 1) ? 12 : thang - 1;
        int prevYear  = (thang == 1) ? nam - 1 : nam;

        double current = Optional
                .ofNullable(donXinNghiRepository.tiLeNghiPhep(thang, nam))
                .orElse(0.0);

        double previous = Optional
                .ofNullable(donXinNghiRepository.tiLeNghiPhep(prevMonth, prevYear))
                .orElse(0.0);

        result.put("tiLe", Math.round(current * 10) / 10.0);
        result.put("chenhLech", Math.round((current - previous) * 10) / 10.0);

        return result;
    }


}