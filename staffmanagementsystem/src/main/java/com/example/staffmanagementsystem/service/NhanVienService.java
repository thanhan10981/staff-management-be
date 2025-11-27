package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NhanVienService {

    List<NhanVienDTO> getAll();

    NhanVienDTO getById(Integer id);

    NhanVienDTO create(NhanVienDTO dto);

    NhanVienDTO update(Integer id, NhanVienDTO dto);

    void delete(Integer id);

    ResponseEntity<?> importExcel(MultipartFile file);

}
