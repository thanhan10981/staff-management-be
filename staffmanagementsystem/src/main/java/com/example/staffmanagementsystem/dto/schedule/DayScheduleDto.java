package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayScheduleDto {
    private LocalDate date;
    private List<ShiftDto> shifts;
}
