package com.example.staffmanagementsystem.entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDungQuyenId implements Serializable {
    private Integer maNguoiDung;
    private Integer maQuyen;
}
