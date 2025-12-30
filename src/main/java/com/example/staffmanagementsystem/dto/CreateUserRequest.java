package com.example.staffmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private Integer maNhanVien;
    private List<Integer> permissionIds;
}