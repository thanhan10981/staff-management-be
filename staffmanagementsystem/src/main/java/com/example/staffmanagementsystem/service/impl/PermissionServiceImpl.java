package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhanQuyenDto;
import com.example.staffmanagementsystem.entity.PhanQuyen;
import com.example.staffmanagementsystem.repository.PhanQuyenRepository;
import com.example.staffmanagementsystem.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PhanQuyenRepository repository;

    public PermissionServiceImpl(PhanQuyenRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PhanQuyenDto> getAll() {
        return repository.findAll().stream().map(p -> new PhanQuyenDto(p.getMaQuyen(), p.getTenQuyen(), p.getMoTa())).collect(Collectors.toList());
    }
}