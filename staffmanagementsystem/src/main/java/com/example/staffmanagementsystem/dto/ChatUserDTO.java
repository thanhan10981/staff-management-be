package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatUserDTO {
    private Integer maNhanVien;
    private String tenDangNhap;
    private String vaiTro;
}
