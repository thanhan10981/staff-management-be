package com.example.staffmanagementsystem.dto.leaveoverview;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTotalStatisticFilterRequest {

    // default: THANG_NAY nếu null
    private LoaiThongKeNgayCong timeRange;

    // optional
    private Integer maPhongBan;
    private String tenPhongBan;
}
