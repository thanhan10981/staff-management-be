package com.example.staffmanagementsystem.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.example.staffmanagementsystem.dto.DonNghiPhepDTO;
import com.example.staffmanagementsystem.service.DonNghiPhepService;

import java.util.List;

@RestController
@RequestMapping("/api/DonNghiPhep")
@RequiredArgsConstructor
public class DonNghiPhepController {

    private final DonNghiPhepService service;

    @GetMapping
    public List<DonNghiPhepDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DonNghiPhepDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public DonNghiPhepDTO create(@RequestBody DonNghiPhepDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public DonNghiPhepDTO update(@PathVariable Integer id, @RequestBody DonNghiPhepDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/search/{keyword}")
    public List<DonNghiPhepDTO> search(@PathVariable String keyword) {
        return service.search(keyword);
    }

    @PutMapping("/{id}/approve")
    public DonNghiPhepDTO approve(@PathVariable Integer id) {
        return service.updateStatus(id, "Da duyet", null);
    }

    @PutMapping("/{id}/reject")
    public DonNghiPhepDTO reject(@PathVariable Integer id) {
        return service.updateStatus(id, "Tu choi", null);
    }

}
