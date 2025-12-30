package com.example.staffmanagementsystem.controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TiLeDungGioDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TiLeDungGioService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cham-cong/thong-ke")
@RequiredArgsConstructor
public class TiLeDungGioController {

    private final TiLeDungGioService service;

    @GetMapping("/ti-le-dung-gio")
    public TiLeDungGioDTO tiLeDungGio(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam String loai,

            @RequestParam(required = false) Integer maPhongBan,
            @RequestParam(required = false) Integer maViTri
    ) {
        return service.tinhTiLeDungGio(ngayChon, loai, maPhongBan, maViTri);
    }
}
