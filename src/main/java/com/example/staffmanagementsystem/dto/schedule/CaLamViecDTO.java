package com.example.staffmanagementsystem.dto.schedule;
import lombok.Data;
import java.time.LocalTime;
import java.math.BigDecimal;


@Data
public class CaLamViecDTO {
    private Integer maCa;
    private String tenCa;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private BigDecimal phuCap;
}
