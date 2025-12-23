package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ChungChiDTO;
import com.example.staffmanagementsystem.service.ChungChiService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ChungChi")
@RequiredArgsConstructor
public class ChungChiController {

    private final ChungChiService service;

    @GetMapping
    public List<ChungChiDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/nhanvien/{id}")
    public List<ChungChiDTO> getByNV(@PathVariable Integer id) {
        return service.getByNhanVien(id);
    }

    @PostMapping
    public ChungChiDTO create(@RequestBody ChungChiDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ChungChiDTO update(@PathVariable Integer id, @RequestBody ChungChiDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
