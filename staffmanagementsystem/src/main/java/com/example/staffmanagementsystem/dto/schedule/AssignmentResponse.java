package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {
    private Integer maPhanCong;
    private String message;
}
