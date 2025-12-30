package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.entity.TinNhan;
import com.example.staffmanagementsystem.repository.TinNhanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final TinNhanRepository tinNhanRepo;

    @GetMapping("/history")
    public List<TinNhan> history(
            @RequestParam Integer u1,
            @RequestParam Integer u2
    ) {
        return tinNhanRepo.getLichSuChat(u1, u2);
    }
}
