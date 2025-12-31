package com.example.staffmanagementsystem.service.schedule;


import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final LichTrucNgayRepository lichTrucNgayRepository;

    @Override
    public List<DayDetailScheduleDTO> getChiTietTheoNgayVaKhoa(
            LocalDate ngayTruc,
            Integer maKhoa
    ) {
        return lichTrucNgayRepository
                .findChiTietTheoNgayVaKhoa(ngayTruc, maKhoa);
    }
}
