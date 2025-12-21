package com.example.staffmanagementsystem.dto.leaveoverview;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalSickLeaveFilterRequest {

    private LoaiThongKeNgayCong timeRange;
    private Integer maPhongBan;
    private String tenPhongBan;
}
