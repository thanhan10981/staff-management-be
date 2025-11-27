package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import java.time.LocalDate;
import java.util.List;

public interface LichTrucService {

    void taoPhanCongTuan(Integer maNV, Integer maPhong, Integer maKhoa, LocalDate start);

    List<LichTrucNgayDTO> getLichTheoTuan(Integer maNV, LocalDate start, LocalDate end);
}
