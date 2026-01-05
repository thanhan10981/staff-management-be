package com.example.staffmanagementsystem.dto.schedule;
import java.math.BigDecimal;
import java.sql.Date;
import lombok.Getter;

@Getter
public class MonthlyScheduleRowDTO {

    private Date ngayTruc;
    private String hoTen;
    private String tenViTri;
    private String tenPhong;
    private String tenCa;
    private BigDecimal tongGioLam; // 👈 PHẢI LÀ BigDecimal
    private String trangThai;

    public MonthlyScheduleRowDTO(
            Date ngayTruc,
            String hoTen,
            String tenViTri,
            String tenPhong,
            String tenCa,
            BigDecimal tongGioLam,
            String trangThai
    ) {
        this.ngayTruc = ngayTruc;
        this.hoTen = hoTen;
        this.tenViTri = tenViTri;
        this.tenPhong = tenPhong;
        this.tenCa = tenCa;
        this.tongGioLam = tongGioLam;
        this.trangThai = trangThai;
    }
}
