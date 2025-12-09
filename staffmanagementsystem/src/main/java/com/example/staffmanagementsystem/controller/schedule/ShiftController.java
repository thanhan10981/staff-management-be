package com.example.staffmanagementsystem.controller.schedule;

import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.dto.schedule.CaLamViecDTO;
import com.example.staffmanagementsystem.dto.schedule.NhanVienScheduleDTO;
import com.example.staffmanagementsystem.service.schedule.ConfigService;
import com.example.staffmanagementsystem.service.schedule.ShiftService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/shifts")

public class ShiftController {
    private final ShiftService shiftService;
    private final ConfigService configService;



    public ShiftController(ShiftService shiftService, ConfigService configService) {
        this.shiftService = shiftService;
        this.configService = configService;
    }


    // Get shifts by khoa (month range optional)
    @GetMapping
    public ResponseEntity<List<LichTrucNgayDTO>> getShiftsByKhoa(@RequestParam Integer maKhoa,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<LichTrucNgayDTO> res = shiftService.getLichTrucByKhoa(maKhoa, from, to);
        return ResponseEntity.ok(res);
    }


    // Assign single shift (one date)
    @PostMapping("/assign")
    public ResponseEntity<?> assignSingle(@RequestBody LichTrucNgayDTO dto) {
        try {
            LichTrucNgayDTO saved = shiftService.assignSingleShift(dto);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    // Create PhanCong and generate LichTrucNgay according to range and repeat
    @PostMapping("/phancong")
    public ResponseEntity<?> createPhanCong(@RequestBody PhanCongCaTrucDTO dto) {
        try {
            List<LichTrucNgayDTO> created = shiftService.createPhanCongAndGenerateLich(dto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @GetMapping("/ca")
    public ResponseEntity<List<CaLamViecDTO>> getAllCa() {
        return ResponseEntity.ok(configService.getAllCa());
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getShiftStats(
            @RequestParam Integer maKhoa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(shiftService.getShiftStats(maKhoa, from, to));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(shiftService.getShiftById(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShift(@PathVariable Integer id, @RequestBody LichTrucNgayDTO dto) {
        try {
            return ResponseEntity.ok(shiftService.updateShift(id, dto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable Integer id) {
        try {
            shiftService.deleteShift(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/phancong/{pcId}")
    public ResponseEntity<?> deleteByPhanCong(@PathVariable Integer pcId) {
        try {
            int deleted = shiftService.deleteShiftsByPhanCong(pcId);
            return ResponseEntity.ok(Map.of("deleted", deleted));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(shiftService.updateShiftStatus(id, status));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/nhanvien")
    public ResponseEntity<?> getNhanVienTheoKhoaPhong(
            @RequestParam Integer maKhoa,
            @RequestParam(required = false) Integer maPhong
    ) {
        return ResponseEntity.ok(
                shiftService.getNhanVienTheoKhoaPhong(maKhoa, maPhong)
        );
    }
}
