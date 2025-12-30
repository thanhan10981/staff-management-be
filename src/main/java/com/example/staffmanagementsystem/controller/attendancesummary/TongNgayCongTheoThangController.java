package com.example.staffmanagementsystem.controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNgayCongTheoThangService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cham-cong/bieu-do")
public class TongNgayCongTheoThangController {

    private final TongNgayCongTheoThangService service;

    public TongNgayCongTheoThangController(TongNgayCongTheoThangService service) {
        this.service = service;
    }

    @GetMapping("/tong-ngay-cong-theo-thang")
    public List<TongNgayCongTheoThangDTO> thongKe(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam(required = false)
            Integer maPhongBan,

            @RequestParam(required = false)
            Integer maViTri
    ) {
        return service.thongKeTheoNam(
                ngayChon,
                maPhongBan,
                maViTri
        );
    }
}
