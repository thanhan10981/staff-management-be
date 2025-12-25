package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "AuditLog")
@Getter
@Setter
public class AttendanceActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaLog")
    private Integer id;  // ← ĐỔI từ Long thành Integer

    @Column(name = "NguoiThucHien")
    private Integer actorUserId;  // int trong DB

    @Column(name = "HanhDong", length = 100, nullable = false)
    private String action;

    @Column(name = "MoTa", length = 255)
    private String details;

    @Column(name = "ThoiGian", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "MaNhanVien")
    private Integer employeeId;  // int trong DB

    // getters/setters (Lombok sẽ sinh đúng)
}