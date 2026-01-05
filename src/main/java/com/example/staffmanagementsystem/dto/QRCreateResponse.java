package com.example.staffmanagementsystem.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class QRCreateResponse {
    private String maQRCode;
    private LocalDateTime expireAt;
}
