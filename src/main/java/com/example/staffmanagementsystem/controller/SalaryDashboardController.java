package com.example.staffmanagementsystem.controller;


import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.entity.CauHinhCaTruc;
import com.example.staffmanagementsystem.entity.LuongThang;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.CauHinhCaTrucRepository;
import com.example.staffmanagementsystem.repository.CauHinhCaTruc_PhongRepository;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.service.SalaryDashboardService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard/salary")
@RequiredArgsConstructor
public class SalaryDashboardController {

    private final SalaryDashboardService dashboardService;
    @Autowired
    private CauHinhCaTrucRepository cauHinhRepo;
    private final LuongThangRepository luongThangRepo;
    // Tổng quan
    @GetMapping
    public DashboardSalaryDto getSalaryOverview(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return dashboardService.getOverview(month, year);
    }

    // Bảng chi tiết lương
    @GetMapping("/detail")
    public List<SalaryDetailDto> getSalaryTable(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return dashboardService.getSalaryDetail(month, year);
    }

    // Chart
    @GetMapping("/chart")
    public SalaryChartDto getSalaryChart(
            @RequestParam int month,
            @RequestParam int year
    ) {
        return dashboardService.getChartData(month, year);
    }


    @PostMapping("/calculate")
    public ResponseEntity<?> calculateSalary(
            @RequestParam int month,
            @RequestParam int year
    ) {
        dashboardService.calculateSalary(month, year);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Đã tính lương tháng " + month + "/" + year
        ));
    }
    @PostMapping("/holiday-coef")
    public ResponseEntity<?> updateHolidayCoef(@RequestBody HolidayCoefRequest req) {

        CauHinhCaTruc config = cauHinhRepo.findById(1).orElseThrow();

        config.setHeSoCuoiTuan(req.getWeekendCoef());
        config.setHeSoNgayLe(req.getHolidayCoef());
        config.setUpdatedAt(LocalDateTime.now());

        cauHinhRepo.save(config);

        return ResponseEntity.ok("Updated");
    }
    @GetMapping("/export-salary")
    public ResponseEntity<byte[]> exportSalary(
            @RequestParam int month,
            @RequestParam int year) throws IOException {

        List<LuongThang> list = luongThangRepo.findByMonthYear(month, year);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Salary");

        // header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Tên nhân viên");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Lương cơ bản");
        header.createCell(3).setCellValue("Giờ làm");
        header.createCell(4).setCellValue("Phụ cấp");
        header.createCell(5).setCellValue("Làm thêm giờ");
        header.createCell(6).setCellValue("Tổng lương");

        int rowIdx = 1;

        for (LuongThang lt : list) {
            NhanVien nv = lt.getNhanVien();
            Row row = sheet.createRow(rowIdx++);

            row.createCell(0).setCellValue(nv.getTenNhanVien());
            row.createCell(1).setCellValue(nv.getEmail());
            row.createCell(2).setCellValue(lt.getLuongCoBan());
            row.createCell(3).setCellValue(160);
            row.createCell(4).setCellValue(lt.getPhuCapCoDinh());
            row.createCell(5).setCellValue(lt.getPhuCapTrucCa());
            row.createCell(6).setCellValue(lt.getTongThuNhap());
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);
        workbook.close();

        byte[] bytes = output.toByteArray();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=salary.xlsx")
                .body(bytes);
    }

    @PutMapping("/update-ot-allowance")
    public ResponseEntity<?> updateOT(@RequestBody List<AllowanceOTRequest> list) {

        for (AllowanceOTRequest req : list) {

            LuongThang lt = luongThangRepo
                    .findByNhanVien_MaNhanVienAndThangAndNam(
                            req.maNhanVien,
                            LocalDate.now().getMonthValue(),
                            LocalDate.now().getYear()
                    ).orElse(null);

            if (lt != null) {
                lt.setPhuCapKhac(req.phuCap);
                lt.setPhuCapTrucCa(req.ot *1L);
                lt.setTongThuNhap(
                        lt.getLuongCoBan()
                                + lt.getPhuCapCoDinh()
                                + lt.getPhuCapTrucCa()
                                + lt.getPhuCapKhac()
                );
                luongThangRepo.save(lt);
            }
        }

        return ResponseEntity.ok("Updated");
    }






}
