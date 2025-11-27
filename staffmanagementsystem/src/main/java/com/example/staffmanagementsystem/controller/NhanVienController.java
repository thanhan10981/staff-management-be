package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.service.NhanVienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/NhanVien")
@RequiredArgsConstructor
public class NhanVienController {

    private final NhanVienService service;

    @GetMapping
    public List<NhanVienDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public NhanVienDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public NhanVienDTO create(@RequestBody NhanVienDTO dto) {
        return service.create(dto);
    }
    //update nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer id,
            @RequestBody NhanVienDTO request
    ) {
        NhanVienDTO updated = service.update(id, request);
        return ResponseEntity.ok(updated);
    }
    // DELETE nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        service.delete(id);

        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("message", "Deleted");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/import-excel")
    public ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.importExcel(file));
    }

}
