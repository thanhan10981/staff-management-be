package com.example.staffmanagementsystem.dto.profile;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongTinCaNhanDto {

    private String hoTen;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String trinhDoChuyenMon;
}
