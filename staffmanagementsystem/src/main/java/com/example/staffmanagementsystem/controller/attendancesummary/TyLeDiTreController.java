package com.example.staffmanagementsystem.controller.attendancesummary;


import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTrePhongBanChartDTO;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TyLeDiTreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cham-cong/thong-ke")
@RequiredArgsConstructor
public class TyLeDiTreController {

    private final TyLeDiTreService service;

    @GetMapping("/ty-le-di-tre")
    public List<TyLeDiTreTheoPhongBanDTO> tyLeDiTre(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,
            @RequestParam LoaiThongKeNgayCong loai
    ) {
        return service.tyLeDiTre(ngayChon, loai);
    }

    @GetMapping("/chart-phong-ban")
    public List<TyLeDiTrePhongBanChartDTO> chartTyLeDiTre(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate ngayChon,

            @RequestParam LoaiThongKeNgayCong loai
    ) {
        return service.tyLeDiTreChart(ngayChon, loai);
    }
}
