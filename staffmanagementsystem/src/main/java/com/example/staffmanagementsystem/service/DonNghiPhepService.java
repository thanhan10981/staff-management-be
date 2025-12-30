package com.example.staffmanagementsystem.service;

import java.util.List;
import com.example.staffmanagementsystem.dto.DonNghiPhepDTO;

public interface DonNghiPhepService {

    List<DonNghiPhepDTO> getAll();
    DonNghiPhepDTO getById(Integer id);
    DonNghiPhepDTO create(DonNghiPhepDTO dto);
    DonNghiPhepDTO update(Integer id, DonNghiPhepDTO dto);
    DonNghiPhepDTO updateStatus(Integer id, String status, String note);

    void delete(Integer id);
    List<DonNghiPhepDTO> search(String keyword);
}
