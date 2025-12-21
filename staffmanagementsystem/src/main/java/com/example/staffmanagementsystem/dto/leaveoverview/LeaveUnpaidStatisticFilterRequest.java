package com.example.staffmanagementsystem.dto.leaveoverview;


import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveUnpaidStatisticFilterRequest {

    private LoaiThongKeNgayCong timeRange;

    // optional
    private Integer maPhongBan;
    private String tenPhongBan;
}
