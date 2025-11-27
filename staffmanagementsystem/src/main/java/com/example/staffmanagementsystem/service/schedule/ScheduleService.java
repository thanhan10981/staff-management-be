package com.example.staffmanagementsystem.service.schedule;

import com.example.staffmanagementsystem.dto.schedule.AssignmentRequest;
import com.example.staffmanagementsystem.dto.schedule.AssignmentResponse;
import com.example.staffmanagementsystem.dto.schedule.DayScheduleDto;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<DayScheduleDto> getSchedules(LocalDate start, LocalDate end, Integer phongId, Integer caId);
    AssignmentResponse assignShift(AssignmentRequest req, Integer actorId);
    void deleteAssignment(Integer assignmentId, Integer actorId);
}
