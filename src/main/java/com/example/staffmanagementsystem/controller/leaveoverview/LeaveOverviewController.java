package com.example.staffmanagementsystem.controller.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.*;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leave-overview")
@RequiredArgsConstructor
public class LeaveOverviewController {

    private final LeaveOverviewService service;

    @PostMapping
    public List<LeaveOverviewDTO> getLeaveOverview(
            @RequestBody LeaveOverviewFilterRequest request
    ) {
        return service.getLeaveOverview(request);
    }
}
