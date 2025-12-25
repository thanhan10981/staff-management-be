package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ChatUserDTO;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatUserController {

    private final NguoiDungRepository nguoiDungRepo;

    @GetMapping("/users")
    public List<ChatUserDTO> users() {
        return nguoiDungRepo.findAll().stream()
                .filter(u -> "HoatDong".equalsIgnoreCase(u.getTrangThai()))
                .filter(u -> u.getMaNhanVien() != null)
                .map(u -> new ChatUserDTO(u.getMaNhanVien(), u.getTenDangNhap(), u.getVaiTro()))
                .toList();
    }
}
