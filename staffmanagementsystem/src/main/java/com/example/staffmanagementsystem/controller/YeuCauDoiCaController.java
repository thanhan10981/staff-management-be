package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateDTO;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaDetailView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaView;
import com.example.staffmanagementsystem.repository.YeuCauDoiCaRepository;
import com.example.staffmanagementsystem.service.YeuCauDoiCaService;
import org.springframework.web.bind.annotation.*;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateView;


import java.util.List;

@RestController
@RequestMapping("/api/YeuCauDoiCa")
@CrossOrigin(origins = "http://localhost:4200")
public class YeuCauDoiCaController {

    private final YeuCauDoiCaService service;

    public YeuCauDoiCaController(YeuCauDoiCaService service) {
        this.service = service;
    }

    @GetMapping
    public List<YeuCauDoiCaView> getAll() {
        return service.getAll();
    }

    // 🔥 DỮ LIỆU CHO POPUP
    @GetMapping("/create-form/{maNhanVien}")
    public YeuCauDoiCaCreateView getCreateForm(
            @PathVariable Integer maNhanVien
    ) {
        return service.getCreateForm(maNhanVien);
    }

    @PostMapping
    public void create(@RequestBody YeuCauDoiCaCreateDTO dto) {
        service.create(dto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public YeuCauDoiCaDetailView getDetail(@PathVariable Integer id) {
        return service.getDetail(id);
    }
}
