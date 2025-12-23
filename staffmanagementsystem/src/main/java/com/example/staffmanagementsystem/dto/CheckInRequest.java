package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class CheckInRequest {
    private String deviceInfo;
    private String locationInfo; // thêm nếu cần, dù ChamCong chưa có cột; lưu vào ThietBi nếu kết hợp
}