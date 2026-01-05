package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ChamCongTodayDTO;
import com.example.staffmanagementsystem.dto.QRCheckinRequest;
import com.example.staffmanagementsystem.service.ChamCongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cham-cong")
@RequiredArgsConstructor
public class ChamCongController {

    private final ChamCongService chamCongService;

    @GetMapping("/today")
    public ChamCongTodayDTO today() {
        return chamCongService.getChamCongToday();
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@RequestParam String thietBi) {
        return ResponseEntity.ok(chamCongService.chamCongBangNut(thietBi));
    }

    @PostMapping("/qr")
    public ResponseEntity<?> qr(@RequestBody QRCheckinRequest request) {
        return ResponseEntity.ok(chamCongService.chamCongBangQR(request));
    }

    @PostMapping("/qr/create")
    public ResponseEntity<?> createQR() {
        return ResponseEntity.ok(chamCongService.taoQRChamCong());
    }

}
