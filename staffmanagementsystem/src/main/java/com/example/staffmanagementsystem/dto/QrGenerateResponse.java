package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class QrGenerateResponse {
    private String token;
    private String qrPayload; // frontend render QR từ payload
    private String message;
}