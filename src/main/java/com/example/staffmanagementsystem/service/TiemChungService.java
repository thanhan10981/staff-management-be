package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.TiemChungDTO;
import java.util.List;

public interface TiemChungService {

    List<TiemChungDTO> getAll();
    List<TiemChungDTO> getByNhanVien(Integer id);
    TiemChungDTO create(TiemChungDTO dto);
}
