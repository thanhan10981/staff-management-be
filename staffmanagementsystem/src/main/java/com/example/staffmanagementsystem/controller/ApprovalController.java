package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ApprovalDTO;
import com.example.staffmanagementsystem.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
@CrossOrigin
public class ApprovalController {

    private final ApprovalService approvalService;

    @GetMapping
    public List<ApprovalDTO> getPending() {
        return approvalService.getPendingApprovals();
    }

    @PutMapping("/{type}/{id}/approve")
    public void approve(@PathVariable String type, @PathVariable Integer id) {
        approvalService.approve(type, id);
    }

    @PutMapping("/{type}/{id}/reject")
    public void reject(@PathVariable String type, @PathVariable Integer id) {
        approvalService.reject(type, id);
    }
}
