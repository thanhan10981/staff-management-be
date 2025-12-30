package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.ApprovalDTO;

import java.util.List;

public interface ApprovalService {

    List<ApprovalDTO> getPendingApprovals();

    void approve(String type, Integer id);

    void reject(String type, Integer id);
}
