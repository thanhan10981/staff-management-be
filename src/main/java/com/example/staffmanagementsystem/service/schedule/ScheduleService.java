package com.example.staffmanagementsystem.service.schedule;

import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {

    List<DayDetailScheduleDTO> getChiTietTheoNgayVaKhoa(
            LocalDate ngayTruc,
            Integer maKhoa
    );


    byte[] exportMonthlyPdf(Integer maKhoa, int year, int month);


}
