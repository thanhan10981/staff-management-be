package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.repository.DonXinNghiRepository;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final LuongThangRepository luongThangRepo;
    private final NhanVienRepository nhanVienRepo;
    private final DonXinNghiRepository donXinNghiRepository;

    /* ================= CONSTRUCTOR ================= */
    public DashboardServiceImpl(
            LuongThangRepository luongThangRepo,
            NhanVienRepository nhanVienRepo,
            DonXinNghiRepository donXinNghiRepository
    ) {
        this.luongThangRepo = luongThangRepo;
        this.nhanVienRepo = nhanVienRepo;
        this.donXinNghiRepository = donXinNghiRepository;
    }

    /* ================= NHÂN VIÊN THEO PHÒNG BAN ================= */
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

    /* ================= NHÂN VIÊN THEO THÁNG ================= */
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

    /* ================= CƠ CẤU LƯƠNG ================= */
    @Override
    public List<DashboardLuongDTO> coCauLuong() {

        List<Object[]> latestList = luongThangRepo.findLatestMonthYear();
        if (latestList == null || latestList.isEmpty()) return List.of();

        Object[] latest = latestList.get(0);
        int thang = ((Number) latest[0]).intValue();
        int nam   = ((Number) latest[1]).intValue();

        List<Object[]> list = luongThangRepo.tongHopLuongTheoThang(thang, nam);
        if (list.isEmpty()) return List.of();

        Object[] r = list.get(0);

        return List.of(
                new DashboardLuongDTO("Lương cơ bản", ((Number) r[0]).doubleValue()),
                new DashboardLuongDTO("Phụ cấp",      ((Number) r[1]).doubleValue()),
                new DashboardLuongDTO("BHXH",          ((Number) r[2]).doubleValue()),
                new DashboardLuongDTO("Thuế TNCN",     ((Number) r[3]).doubleValue())
        );
    }

    /* ================= TỔNG NHÂN VIÊN ================= */
    @Override
    public Map<String, Object> thongKeNhanVien() {

        Map<String, Object> result = new HashMap<>();

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

    /* ================= TỈ LỆ NGHỈ PHÉP ================= */
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

    /* ================= QUỸ LƯƠNG THEO PHÒNG BAN ================= */
    @Override
    public List<DashboardLuongDTO> coCauLuongbaocao(
            String period,
            Integer dept
    ) {
        Integer month = null;
        Integer year  = LocalDate.now().getYear();

        // xử lý period
        switch (period) {
            case "month" -> month = LocalDate.now().getMonthValue();
            case "year" -> month = null;
            default -> month = LocalDate.now().getMonthValue();
        }

        List<Object[]> list =
                luongThangRepo.coCauLuongBaoCao(month, year, dept);

        if (list == null || list.isEmpty()) return List.of();

        Object[] r = list.get(0);

        return List.of(
                new DashboardLuongDTO("Lương cơ bản", ((Number) r[0]).doubleValue()),
                new DashboardLuongDTO("Phụ cấp",      ((Number) r[1]).doubleValue()),
                new DashboardLuongDTO("BHXH",          ((Number) r[2]).doubleValue()),
                new DashboardLuongDTO("Thuế TNCN",     ((Number) r[3]).doubleValue())
        );
    }
    @Override
    public List<QuyLuongPhongBanDto> getQuyLuongTheoPhongBan(
            String period,
            Integer dept,
            String role
    ) {
        Integer month = null;
        Integer year = LocalDate.now().getYear();

        if ("month".equals(period)) {
            month = LocalDate.now().getMonthValue();
        }

        return luongThangRepo.filterQuyLuong(
                dept,
                month,
                year,
                role
        );
    }
    @Override
    public List<BangLuongNhanVienDTO> getBangLuongNhanVien(
            String period,
            Integer dept,
            String role
    ) {
        Integer month = null;
        Integer year = LocalDate.now().getYear();

        if ("month".equals(period)) {
            month = LocalDate.now().getMonthValue();
        }

        return luongThangRepo.filterBangLuongNhanVien(
                dept,
                role,
                month,
                year
        );
    }

    public KpiLuongDTO getKpiLuongBaoCao(
            String period,
            Integer dept,
            String role
    ) {
        Integer month = null;
        Integer year = LocalDate.now().getYear();

        if ("month".equals(period)) {
            month = LocalDate.now().getMonthValue();
        }

        return luongThangRepo.thongKeKpiLuong(dept, role, month, year);
    }
    public QuyLuongThangDTO getQuyLuongThang() {

        LocalDate now = LocalDate.now();

        int thang = now.getMonthValue();
        int nam = now.getYear();

        int thangTruoc = (thang == 1) ? 12 : thang - 1;
        int namTruoc = (thang == 1) ? nam - 1 : nam;

        Object[] raw = luongThangRepo.thongKeQuyLuongThang(
                thang, nam, thangTruoc, namTruoc
        );

        // 🔥 LẤY OBJECT[] BÊN TRONG
        Object[] r = (Object[]) raw[0];

        double hienTai = ((Number) r[0]).doubleValue();
        double truoc   = ((Number) r[1]).doubleValue();

        double tyLe = truoc == 0 ? 0 : ((hienTai - truoc) / truoc) * 100;

        return new QuyLuongThangDTO(
                hienTai,
                truoc,
                Math.round(tyLe * 10) / 10.0
        );
    }

}
