package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.UserResponseDTO;
import com.example.staffmanagementsystem.service.NguoiDungService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.example.staffmanagementsystem.dto.CreateUserRequest;
import com.example.staffmanagementsystem.dto.UpdateUserRequest;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminUserController {
    private final NguoiDungService nguoiDungService;

    public AdminUserController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return nguoiDungService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
        nguoiDungService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-many")
    public ResponseEntity<?> deleteMany(@RequestBody List<Integer> ids) {
        nguoiDungService.deleteMany(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        nguoiDungService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        nguoiDungService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }
}