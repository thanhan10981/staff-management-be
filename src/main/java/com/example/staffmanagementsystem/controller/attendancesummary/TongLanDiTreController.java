package com.example.staffmanagementsystem.controller.attendancesummary;


import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongLanDiTreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cham-cong/thong-ke")
@RequiredArgsConstructor
public class TongLanDiTreController {

    private final TongLanDiTreService service;

    @GetMapping("/tong-lan-di-tre")
    public TongLanDiTreDTO tongLanDiTre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam String loai,

            @RequestParam(required = false) Integer maPhongBan,
            @RequestParam(required = false) Integer maViTri
    ) {
        return service.tinhTongLanDiTre(
                ngayChon,
                loai,
                maPhongBan,
                maViTri
        );
    }
}
