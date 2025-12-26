package com.example.staffmanagementsystem.dto.profile;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThongTinLienHeCongViecDto {

    private String email;
    private String sdt;
    private Integer maNhanVien;
    private String tenPhongBan;
}
