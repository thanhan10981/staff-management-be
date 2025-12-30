package com.example.staffmanagementsystem.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "DonNghiPhep")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonNghiPhep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDon")
    private Integer id;

    // FK → NhanVien
    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    @Column(name = "LoaiNghi")
    private String loaiNghi;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "LyDo")
    private String lyDo;

    @Column(name = "TrangThai")
    private String trangThai;
}
