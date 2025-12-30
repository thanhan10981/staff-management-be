package com.example.staffmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRequest {
    private String tenDangNhap;
    private String vaiTro;
    private Integer maNhanVien;
    private List<Integer> permissionIds;
}