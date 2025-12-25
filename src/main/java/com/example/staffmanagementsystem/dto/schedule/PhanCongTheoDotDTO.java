package com.example.staffmanagementsystem.dto.schedule;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class PhanCongTheoDotDTO {

    private Integer maKhoa;       // id khoa
    private Integer maPhong;      // id phòng vật lý
    private Integer maCa;         // id ca
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private String ghiChu;
    private String nguoiTao;

    private List<Integer> danhSachNhanVien;   // list nhân viên
}
