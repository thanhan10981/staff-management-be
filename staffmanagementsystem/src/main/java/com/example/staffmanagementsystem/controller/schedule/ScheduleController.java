package com.example.staffmanagementsystem.controller.schedule;

import com.example.staffmanagementsystem.dto.schedule.AssignmentRequest;
import com.example.staffmanagementsystem.dto.schedule.AssignmentResponse;
import com.example.staffmanagementsystem.dto.schedule.DayScheduleDto;
import com.example.staffmanagementsystem.service.schedule.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")  // ⭐ FE đang gọi /api/schedules → để đúng REST chuẩn
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    // ===========================================================
    // GET lịch theo tháng / range
    // FE đang gọi: GET /api/schedules?year=2025&month=11
    // ===========================================================
    @GetMapping
    public ResponseEntity<?> getSchedulesByMonth(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Integer phongId,
            @RequestParam(required = false) Integer caId
    ) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<DayScheduleDto> result = service.getSchedules(start, end, phongId, caId);
        return ResponseEntity.ok(result);
    }

    // ===========================================================
    // CREATE SCHEDULE (Tạo lịch)
    // FE gọi: POST /api/schedules (body: AssignmentRequest)
    // ===========================================================
    @PreAuthorize("hasAuthority('TaoLich') or hasRole('QUANTRIVIEN')")
    @PostMapping
    public ResponseEntity<AssignmentResponse> create(
            @RequestBody AssignmentRequest req,
            @RequestParam Integer actorId
    ) {
        AssignmentResponse resp = service.assignShift(req, actorId);
        return ResponseEntity.ok(resp);
    }

    // ===========================================================
    // DELETE SCHEDULE
    // FE gọi: DELETE /api/schedules/{id}
    // ===========================================================
    @PreAuthorize("hasAuthority('TaoLich') or hasRole('QUANTRIVIEN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id,
            @RequestParam Integer actorId
    ) {
        service.deleteAssignment(id, actorId);
        return ResponseEntity.ok().build();
    }
}
