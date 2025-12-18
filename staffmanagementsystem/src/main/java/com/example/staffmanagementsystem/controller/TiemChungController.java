package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.TiemChungDTO;
import com.example.staffmanagementsystem.service.TiemChungService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiemchung")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TiemChungController {

    private final TiemChungService service;

    @GetMapping
    public List<TiemChungDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/nhanvien/{id}")
    public List<TiemChungDTO> getByNhanVien(@PathVariable Integer id) {
        return service.getByNhanVien(id);
    }

    @PostMapping
    public TiemChungDTO create(@RequestBody TiemChungDTO dto) {
        return service.create(dto);
    }
}
