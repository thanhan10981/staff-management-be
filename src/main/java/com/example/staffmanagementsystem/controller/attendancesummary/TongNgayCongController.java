package com.example.staffmanagementsystem.controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNgayCongService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@RestController
@RequestMapping("/api/cham-cong/tong-ngay-cong")
public class TongNgayCongController {

    private final TongNgayCongService service;

    public TongNgayCongController(TongNgayCongService service) {
        this.service = service;
    }

    @GetMapping
    public TongNgayCongDTO tinhTongNgayCong(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam
            LoaiThongKeNgayCong loai,

            @RequestParam(required = false)
            Integer maPhongBan,

            @RequestParam(required = false)
            Integer maViTri
    ) {
        return service.tinhTongNgayCong(
                ngayChon,
                loai,
                maPhongBan,
                maViTri
        );
    }
}
