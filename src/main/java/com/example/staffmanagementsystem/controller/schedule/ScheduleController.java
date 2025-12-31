package com.example.staffmanagementsystem.controller.schedule;

import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;

import com.example.staffmanagementsystem.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Popup Chi tiết lịch làm việc theo ngày
     */
    @GetMapping("/chi-tiet")
    public List<DayDetailScheduleDTO> getChiTiet(
            @RequestParam("ngayTruc") String ngayTruc,
            @RequestParam("maKhoa") Integer maKhoa
    ) {
        return scheduleService.getChiTietTheoNgayVaKhoa(
                LocalDate.parse(ngayTruc),
                maKhoa
        );
    }
}
