package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateDTO;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaDetailView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaView;

import java.util.List;

public interface YeuCauDoiCaService {

    List<YeuCauDoiCaView> getAll();

    YeuCauDoiCaCreateView getCreateForm(Integer nhanVienId);

    YeuCauDoiCaDetailView getDetail(Integer id);

    void create(YeuCauDoiCaCreateDTO dto);

    void delete(Integer id);
}

