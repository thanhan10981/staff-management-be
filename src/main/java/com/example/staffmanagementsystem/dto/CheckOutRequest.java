package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class CheckOutRequest {
    private String deviceInfo;
    private String locationInfo;
}