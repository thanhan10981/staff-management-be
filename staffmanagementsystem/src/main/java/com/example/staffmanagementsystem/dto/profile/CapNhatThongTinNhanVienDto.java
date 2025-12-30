package com.example.staffmanagementsystem.dto.profile;
 import lombok.*;

 import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CapNhatThongTinNhanVienDto {

    private String hoTen;
    private String anhDaiDien;
    private String email;
    private LocalDate ngaySinh;
    private String sdt;
    private Boolean gioiTinh;
}
