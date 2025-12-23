package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.DashboardSalaryDto;
import com.example.staffmanagementsystem.dto.SalaryChartDto;
import com.example.staffmanagementsystem.dto.SalaryDetailDto;
import com.example.staffmanagementsystem.entity.LuongPhuCap;
import com.example.staffmanagementsystem.entity.LuongThang;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.LuongPhuCapRepository;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.SalaryDashboardService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryDashboardServiceImpl implements SalaryDashboardService {

    private final LuongThangRepository luongThangRepo;
    private final NhanVienRepository nhanVienRepo;
    private final LuongPhuCapRepository luongPhuCapRepo;
    private final EntityManager entityManager;

    // ============================
    //   OVERVIEW
    // ============================
    @Override
    public DashboardSalaryDto getOverview(int month, int year) {

        List<LuongThang> list = luongThangRepo.findByMonthYear(month, year);

        long totalSalary = list.stream()
                .mapToLong(LuongThang::getTongThuNhap)
                .sum();

        long employeePaid = list.size();

        // ============================
        //   TOTAL HOURS (SAFE VERSION)
        // ============================
        Object result = entityManager.createNativeQuery("""
                SELECT SUM(DATEDIFF(HOUR, c.ThoiGianVao, c.ThoiGianRa))
                FROM ChamCong c
                WHERE MONTH(c.ThoiGianVao) = :m AND YEAR(c.ThoiGianVao) = :y
                """)
                .setParameter("m", month)
                .setParameter("y", year)
                .getSingleResult();

        Long totalHours = result == null ? 0L : ((Number) result).longValue();

        // ============================
        //   TOTAL ALLOWANCE / OT
        // ============================
        long totalAllowance = list.stream()
                .mapToLong(lt ->
                        luongPhuCapRepo.findByLuongThang(lt.getMaLuong())
                                .stream()
                                .mapToLong(LuongPhuCap::getSoTien)
                                .sum()
                ).sum();

        DashboardSalaryDto dto = new DashboardSalaryDto();
        dto.setTotalSalary(totalSalary);
        dto.setEmployeePaid(employeePaid);
        dto.setTotalHours(totalHours);
        dto.setTotalAllowanceOT(totalAllowance);

        return dto;
    }

    // ============================
    //   SALARY DETAIL TABLE
    // ============================
    @Override
    public List<SalaryDetailDto> getSalaryDetail(int month, int year) {

        List<LuongThang> list = luongThangRepo.findByMonthYear(month, year);
        List<SalaryDetailDto> result = new ArrayList<>();

        for (LuongThang lt : list) {

            NhanVien nv = lt.getNhanVien();
            if (nv == null) continue;

            SalaryDetailDto dto = new SalaryDetailDto();
            dto.setMaNhanVien(nv.getMaNhanVien());
            dto.setTenNhanVien(nv.getTenNhanVien());
            dto.setEmail(nv.getEmail());

            // ⚡ NULL-SAFE CHO TOÀN BỘ FIELD LƯƠNG
            dto.setLuongCoBan(
                    lt.getLuongCoBan() == null ? 0 : lt.getLuongCoBan()
            );


            dto.setGioLamViec(160);

            dto.setKhoanDacBiet(
                    lt.getPhuCapKhac() == null ? 0 : lt.getPhuCapKhac()
            );

            dto.setPhuCap(
                    lt.getPhuCapCoDinh() == null ? 0 : lt.getPhuCapCoDinh()
            );

            dto.setLamThemGio(
                    lt.getPhuCapTrucCa() == null ? 0 : lt.getPhuCapTrucCa()
            );

            dto.setTongLuong(
                    lt.getTongThuNhap() == null ? 0 : lt.getTongThuNhap()
            );

            result.add(dto);
        }

        return result;
    }

    // ============================
    //   CHART DATA
    // ============================
    @Override
    public SalaryChartDto getChartData(int month, int year) {

        List<Object[]> rows = (List<Object[]>) entityManager.createNativeQuery("""
                SELECT 
                    DAY(c.ThoiGianVao),
                    SUM(DATEDIFF(HOUR, c.ThoiGianVao, c.ThoiGianRa))
                FROM ChamCong c
                WHERE MONTH(c.ThoiGianVao) = :m
                  AND YEAR(c.ThoiGianVao) = :y
                GROUP BY DAY(c.ThoiGianVao)
                ORDER BY DAY(c.ThoiGianVao)
                """)
                .setParameter("m", month)
                .setParameter("y", year)
                .getResultList();

        List<String> days = new ArrayList<>();
        List<Long> hours = new ArrayList<>();

        for (Object[] row : rows) {
            days.add("Ngày " + row[0]);
            hours.add(((Number) row[1]).longValue());
        }

        SalaryChartDto dto = new SalaryChartDto();
        dto.setDays(days);
        dto.setOnTime(hours);
        dto.setLate(hours); // Nếu bạn muốn OT riêng → mình sẽ viết query khác

        return dto;
    }
    @Override
    @Transactional
    public void calculateSalary(int month, int year) {

        List<NhanVien> employees = nhanVienRepo.findAll();

        for (NhanVien nv : employees) {

            // 🔥 1. Lấy giờ công (null-safe)
            Object result = entityManager.createNativeQuery("""
                SELECT SUM(DATEDIFF(HOUR, c.ThoiGianVao, c.ThoiGianRa))
                FROM ChamCong c
                WHERE c.MaNV = :id
                  AND MONTH(c.ThoiGianVao)= :m
                  AND YEAR(c.ThoiGianVao)= :y
                """)
                    .setParameter("id", nv.getMaNhanVien())
                    .setParameter("m", month)
                    .setParameter("y", year)
                    .getSingleResult();

            Long totalHours = (result == null) ? 0L : ((Number) result).longValue();

            long baseSalary = 50;
            long allowance = 500000;     // FIXED EXAMPLE
            long otSalary = totalHours > 160 ? (totalHours - 160) * 30000 : 0;

            // 🔥 2. KIỂM TRA LƯƠNG THÁNG NÀY ĐÃ CÓ CHƯA
            LuongThang lt = luongThangRepo
                    .findByNhanVien_MaNhanVienAndThangAndNam(nv.getMaNhanVien(), month, year)
                    .orElse(null);

            // 🔥 3. Nếu chưa có thì tạo mới
            if (lt == null) {
                lt = new LuongThang();
                lt.setNhanVien(nv);
                lt.setThang(month);
                lt.setNam(year);
            }

            // 🔥 4. Cập nhật dữ liệu
            lt.setLuongCoBan(baseSalary);
            lt.setPhuCapCoDinh(allowance);
            lt.setPhuCapTrucCa(otSalary);
            lt.setTongThuNhap(baseSalary + allowance + otSalary);

            // 🔥 5. Lưu — Hibernate tự hiểu NEW → insert, CÓ ID → update
            luongThangRepo.save(lt);
        }
    }

    @Override
    public void updateHolidayCoef(double holidayCoef, double weekendCoef) {

    }


}
