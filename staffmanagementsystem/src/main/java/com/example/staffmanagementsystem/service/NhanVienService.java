package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import java.util.List;

public interface NhanVienService {

    List<NhanVienDTO> getAll();

    NhanVienDTO getById(Integer id);

    NhanVienDTO create(NhanVienDTO dto);

    NhanVienDTO update(Integer id, NhanVienDTO dto);

    void delete(Integer id);

}
