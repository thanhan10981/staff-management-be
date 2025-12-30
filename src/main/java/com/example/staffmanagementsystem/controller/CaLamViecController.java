package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.entity.CaLamViec;
import com.example.staffmanagementsystem.repository.CaLamViecRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/CaLamViec")
@CrossOrigin(origins = "*")
public class CaLamViecController {

    private final CaLamViecRepository repository;

    public CaLamViecController(CaLamViecRepository repository) {
        this.repository = repository;
    }

    // ✅ API lấy danh sách ca làm việc
    @GetMapping
    public List<CaLamViec> getAll() {
        return repository.findAll();
    }
}
