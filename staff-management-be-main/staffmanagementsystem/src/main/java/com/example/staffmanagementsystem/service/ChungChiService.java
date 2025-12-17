package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.ChungChiDTO;
import java.util.List;

public interface ChungChiService {

    List<ChungChiDTO> getAll();

    List<ChungChiDTO> getByNhanVien(Integer maNhanVien);

    ChungChiDTO create(ChungChiDTO dto);

    ChungChiDTO update(Integer id, ChungChiDTO dto);

    void delete(Integer id);
}
