package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.entity.Khoa;
import com.example.staffmanagementsystem.repository.KhoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhoaService {

    private final KhoaRepository repo;

    public List<Khoa> getAll() {
        return repo.findAll();
    }
}
