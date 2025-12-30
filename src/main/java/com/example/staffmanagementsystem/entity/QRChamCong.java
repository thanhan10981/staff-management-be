package com.example.staffmanagementsystem.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QR_ChamCong")
public class QRChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQR")
    private Integer maQR;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "MaQRCode")
    private String maQRCode;

    @Column(name = "TrangThai")
    private String trangThai;
}
