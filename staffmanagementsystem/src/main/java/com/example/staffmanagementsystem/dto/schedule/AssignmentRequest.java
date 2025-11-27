package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentRequest {

    private Integer maNhanVien;
    private Integer maCa;
    private Integer maPhong;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate ngayTruc;

    private String ghiChu;
}
