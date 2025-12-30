package com.example.staffmanagementsystem.controller.leaveoverview;


import com.example.staffmanagementsystem.dto.leaveoverview.*;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveStatsService;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveTotalStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-statistic")
@RequiredArgsConstructor

public class LeaveStatsPanelController {

    private final LeaveTotalStatisticService service;
    private final LeaveStatsService leaveStatsService;

    @PostMapping("/total-annual-leave")
    public LeaveTotalStatisticDTO getTotalAnnualLeave(
            @RequestBody LeaveTotalStatisticFilterRequest request
    ) {
        return service.getTotalLeaveStatistic(request);
    }

    @PostMapping("/total-sick-leave")
    public TotalSickLeaveDaysDTO getTotalSickLeaveDays(
            @RequestBody TotalSickLeaveFilterRequest request) {
        return leaveStatsService.getTotalSickLeaveDays(request);
    }

    @PostMapping("/unpaid-total")
    public TotalUnpaidLeaveDaysDTO getTotalUnpaidLeaveDays(
            @RequestBody LeaveUnpaidStatisticFilterRequest request) {

        return leaveStatsService.getTotalUnpaidLeaveDays(request);
    }


    @GetMapping("/leave-types")
    public List<LeaveTypeDTO> getLeaveTypes() {
        return leaveStatsService.getAllLeaveTypes();
    }
}
