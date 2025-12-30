package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveTotalStatisticDTO;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveTotalStatisticFilterRequest;

public interface LeaveTotalStatisticService {

    LeaveTotalStatisticDTO getTotalLeaveStatistic(
            LeaveTotalStatisticFilterRequest request
    );
}
