package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLog;

    @Column(name = "NguoiThucHien")
    private Integer nguoiThucHien;

    @Column(name = "HanhDong", columnDefinition = "NVARCHAR(255)")
    private String hanhDong;

    @Column(name = "ThoiGian")
    private LocalDateTime thoiGian;

    @Column(name = "MoTa", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "MaNhanVien")
    private Integer maNhanVien;
}
