package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.AttendanceCheckInRequest;
import com.example.staffmanagementsystem.dto.AttendanceHistoryDto;
import com.example.staffmanagementsystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(
            @RequestBody AttendanceCheckInRequest req
    ) {
        String result = attendanceService.checkInFromFE(req);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/check-out")
    public ResponseEntity<String> checkOut(@RequestParam(required = false) String deviceInfo,
                                           @RequestParam(required = false) String locationInfo) {
        String result = attendanceService.checkOut(deviceInfo, locationInfo);
        return ResponseEntity.ok(result);
    }

    /*@GetMapping("/history")
    public ResponseEntity<?> history() {
        return ResponseEntity.ok(attendanceService.historyForCurrentEmployee());
    }*/
    @GetMapping("/history")
    public ResponseEntity<List<AttendanceHistoryDto>> getHistory() {
        return ResponseEntity.ok(attendanceService.getHistory());
    }
}