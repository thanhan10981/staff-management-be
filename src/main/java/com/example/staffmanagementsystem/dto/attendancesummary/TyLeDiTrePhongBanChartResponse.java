package com.example.staffmanagementsystem.dto.attendancesummary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TyLeDiTrePhongBanChartResponse
        implements TyLeDiTrePhongBanChartDTO {

    private String tenPhongBan;
    private Double tiLe;
}
