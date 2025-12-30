package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private Integer nguoiGui;
    private Integer nguoiNhan;
    private String noiDung;
    private LocalDateTime thoiGian;
}
