package com.example.staffmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AuditLogResponseDTO {

    private Integer maLog;
    private String tenDangNhap;
    private String vaiTro;
    private LocalDateTime thoiGian;
    private String hanhDong;
    private String trangThai;

    public AuditLogResponseDTO(
            Integer maLog,
            String tenDangNhap,
            String vaiTro,
            LocalDateTime thoiGian,
            String hanhDong,
            String trangThai
    ) {
        this.maLog = maLog;
        this.tenDangNhap = tenDangNhap;
        this.vaiTro = vaiTro;
        this.thoiGian = thoiGian;
        this.hanhDong = hanhDong;
        this.trangThai = trangThai;
    }
}
