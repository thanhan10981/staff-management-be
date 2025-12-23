package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.PhanQuyenDto;
import com.example.staffmanagementsystem.service.PermissionService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<PhanQuyenDto> getAll() {
        return permissionService.getAll();
    }
}