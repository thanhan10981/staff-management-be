package com.example.staffmanagementsystem.controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNghiKhongPhepService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cham-cong/thong-ke")
@RequiredArgsConstructor
public class TongNghiKhongPhepController {

    private final TongNghiKhongPhepService service;

    @GetMapping("/tong-nghi-khong-phep")
    public TongNghiKhongPhepDTO tongNghiKhongPhep(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam String loai,

            @RequestParam(required = false) Integer maPhongBan,
            @RequestParam(required = false) Integer maViTri
    ) {
        return service.tinhTongNghiKhongPhep(
                ngayChon,
                loai,
                maPhongBan,
                maViTri
        );
    }
}
