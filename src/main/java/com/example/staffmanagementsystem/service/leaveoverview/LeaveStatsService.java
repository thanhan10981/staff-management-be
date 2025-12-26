package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.*;

import java.util.List;

public interface LeaveStatsService {

    TotalSickLeaveDaysDTO getTotalSickLeaveDays(
            TotalSickLeaveFilterRequest request
    );

    TotalUnpaidLeaveDaysDTO getTotalUnpaidLeaveDays(
            LeaveUnpaidStatisticFilterRequest request
    );

    List<LeaveTypeDTO> getAllLeaveTypes();
}

