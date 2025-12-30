package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTrePhongBanChartDTO;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;

import java.time.LocalDate;
import java.util.List;

public interface TyLeDiTreService {

    List<TyLeDiTreTheoPhongBanDTO> tyLeDiTre(
            LocalDate ngayChon,
            LoaiThongKeNgayCong loai
    );

    List<TyLeDiTrePhongBanChartDTO> tyLeDiTreChart(
            LocalDate ngayChon,
            LoaiThongKeNgayCong loai
    );


}
