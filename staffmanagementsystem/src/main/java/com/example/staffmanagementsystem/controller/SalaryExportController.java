package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.SalaryDetailDto;
import com.example.staffmanagementsystem.entity.LuongThang;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.utils.ExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/export/salary")
@RequiredArgsConstructor
public class SalaryExportController {

    private final LuongThangRepository luongThangRepo;

    @GetMapping("/filter")
    public ResponseEntity<?> filterSalary(
            @RequestParam(required = false) Integer  department,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String keyword
    ) {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        int fromMonth = fromDate.getMonthValue();
        int fromYear  = fromDate.getYear();

        int toMonth = toDate.getMonthValue();
        int toYear  = toDate.getYear();

        List<LuongThang> list = luongThangRepo.filterSalary(
                department,
                fromMonth, fromYear,
                toMonth, toYear,
                keyword
        );

        List<SalaryDetailDto> result = list.stream().map(lt -> {
            SalaryDetailDto dto = new SalaryDetailDto();
            dto.setTenNhanVien(lt.getNhanVien().getTenNhanVien());
            dto.setEmail(lt.getNhanVien().getEmail());
            dto.setPhongBan(lt.getNhanVien().getPhongBan().getTenPhongBan());
            dto.setLuongCoBan(lt.getLuongCoBan());
            dto.setPhuCap(lt.getPhuCapCoDinh() + lt.getPhuCapKhac());
            dto.setOt(lt.getPhuCapTrucCa());
            dto.setTongLuong(lt.getTongThuNhap());
            return dto;
        }).collect(Collectors.toList());


        return ResponseEntity.ok(result);
    }
    @GetMapping("/export-excel")
    public ResponseEntity<byte[]> exportExcel(
            @RequestParam(required = false) Integer department,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(required = false) String keyword
    ) throws IOException {

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        List<LuongThang> list = luongThangRepo.filterSalary(
                department,
                fromDate.getMonthValue(), fromDate.getYear(),
                toDate.getMonthValue(), toDate.getYear(),
                keyword
        );

        // ⭐ MAP sang DTO và xử lý NULL SAFE
        List<SalaryDetailDto> result = list.stream().map(lt -> {
            SalaryDetailDto dto = new SalaryDetailDto();

            dto.setTenNhanVien(lt.getNhanVien().getTenNhanVien());
            dto.setEmail(lt.getNhanVien().getEmail());
            dto.setPhongBan(lt.getNhanVien().getPhongBan().getTenPhongBan());
            dto.setLuongCoBan(lt.getLuongCoBan());

            long coDinh = lt.getPhuCapCoDinh() != null ? lt.getPhuCapCoDinh() : 0L;
            long khac   = lt.getPhuCapKhac()   != null ? lt.getPhuCapKhac()   : 0L;
            long ot     = lt.getPhuCapTrucCa() != null ? lt.getPhuCapTrucCa() : 0L;

            dto.setPhuCap(coDinh + khac);
            dto.setOt(ot);
            dto.setTongLuong(lt.getTongThuNhap() != null ? lt.getTongThuNhap() : 0L);

            return dto;
        }).toList();

        // ⭐ Export DTO thay vì entity raw
        byte[] file = ExcelExporter.export(result);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=bang-luong.xlsx")
                .body(file);
    }



}
