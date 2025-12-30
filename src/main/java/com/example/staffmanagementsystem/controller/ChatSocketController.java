package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.ChatMessageDTO;
import com.example.staffmanagementsystem.entity.TinNhan;
import com.example.staffmanagementsystem.repository.TinNhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final TinNhanRepository tinNhanRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageDTO dto) {

        // Lưu DB
        TinNhan tn = new TinNhan();
        tn.setNguoiGui(dto.getNguoiGui());
        tn.setNguoiNhan(dto.getNguoiNhan());
        tn.setNoiDung(dto.getNoiDung());
        tn.setThoiGianGui(LocalDateTime.now());
        tn.setTrangThai("DA_GUI");

        tinNhanRepo.save(tn);

        // Gửi realtime cho người nhận
        messagingTemplate.convertAndSendToUser(
                dto.getNguoiNhan().toString(),
                "/queue/messages",
                dto
        );

        // Gửi lại cho người gửi (sync UI)
        messagingTemplate.convertAndSendToUser(
                dto.getNguoiGui().toString(),
                "/queue/messages",
                dto
        );
    }
}
