package com.example.staffmanagementsystem.entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CauHinhId implements Serializable {
    private Integer maPhong;
    private Integer maCa;
}
