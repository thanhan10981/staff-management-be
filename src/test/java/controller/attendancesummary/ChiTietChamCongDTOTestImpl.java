package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;

import java.time.LocalDate;

public class ChiTietChamCongDTOTestImpl implements ChiTietChamCongDTO {

    private final String tenNhanVien;
    private final String email;
    private final String tenPhongBan;
    private final String tenViTri;
    private final LocalDate ngayCong;
    private final Integer coDiLam;
    private final Integer diTre;
    private final Integer nghiKhongPhep;
    private final Integer nghiCoPhep;

    public ChiTietChamCongDTOTestImpl(
            String tenNhanVien,
            String email
    ) {
        this.tenNhanVien = tenNhanVien;
        this.email = email;
        this.tenPhongBan = "Khoa Nội";
        this.tenViTri = "Bác sĩ";
        this.ngayCong = LocalDate.of(2025, 12, 1);
        this.coDiLam = 1;
        this.diTre = 0;
        this.nghiKhongPhep = 0;
        this.nghiCoPhep = 0;
    }

    @Override
    public String getTenNhanVien() {
        return tenNhanVien;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getTenPhongBan() {
        return tenPhongBan;
    }

    @Override
    public String getTenViTri() {
        return tenViTri;
    }

    @Override
    public LocalDate getNgayCong() {
        return ngayCong;
    }

    @Override
    public Integer getCoDiLam() {
        return coDiLam;
    }

    @Override
    public Integer getDiTre() {
        return diTre;
    }

    @Override
    public Integer getNghiKhongPhep() {
        return nghiKhongPhep;
    }

    @Override
    public Integer getNghiCoPhep() {
        return nghiCoPhep;
    }
}
