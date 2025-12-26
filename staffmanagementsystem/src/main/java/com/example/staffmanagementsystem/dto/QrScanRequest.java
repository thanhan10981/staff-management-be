package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class QrScanRequest {
    private String token;      // token chuẩn nếu FE gửi { token: "..." }
    private String maQRCode;   // alias để nhận { maQRCode: "..." } từ frontend
    private String deviceInfo;
    private String locationInfo;
}
