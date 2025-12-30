package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.LuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/luong")
@RequiredArgsConstructor
public class LuongController {

    private final LuongService luongService;

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<?> getSalary(@PathVariable("maNV") Integer maNV) {
        var dto = luongService.getSalaryOfNhanVien(maNV);
        return ResponseEntity.ok(dto);
    }

}
