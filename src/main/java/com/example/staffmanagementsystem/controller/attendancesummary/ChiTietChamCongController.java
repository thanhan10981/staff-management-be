package com.example.staffmanagementsystem.controller.attendancesummary;


import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;
import com.example.staffmanagementsystem.service.ChiTietChamCongService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/cham-cong/chi-tiet")
public class ChiTietChamCongController {


    private final ChiTietChamCongService service;

    public ChiTietChamCongController(ChiTietChamCongService service) {
        this.service = service;
    }

    @GetMapping("/ngay")
    public List<ChiTietChamCongDTO> chiTietChamCong(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tuNgay,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate denNgay,
            @RequestParam(required = false) Integer maPhongBan,
            @RequestParam(required = false) Integer maViTri
    ) {
        return service.chiTietChamCong(
                tuNgay,
                denNgay,
                maPhongBan,
                maViTri
        );
    }
}
