package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogDTO {

    private String hanhDong;
    private String moTa;
    private LocalDateTime thoiGian;
    private String tenNguoiThucHien;
}
