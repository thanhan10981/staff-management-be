package com.example.staffmanagementsystem.dto.leaveoverview;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveOverviewFilterRequest {

    private LoaiThongKeNgayCong timeRange;

    // Có thể truyền 1 trong 2
    private Integer maPhongBan;
    private String tenPhongBan;
}
