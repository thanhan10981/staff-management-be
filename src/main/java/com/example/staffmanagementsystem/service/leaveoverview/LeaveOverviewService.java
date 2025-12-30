package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewDTO;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewFilterRequest;

import java.util.List;

public interface LeaveOverviewService {

    List<LeaveOverviewDTO> getLeaveOverview(LeaveOverviewFilterRequest request);
}
