package com.example.staffmanagementsystem.service.common;

import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.utils.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentNhanVienService {

    private final CurrentUserService currentUserService;
    private final NguoiDungRepository nguoiDungRepository;

    public Integer getMaNhanVien() {
        Integer maNguoiDung = currentUserService.getCurrentUserId();

        return nguoiDungRepository
                .findMaNhanVienByMaNguoiDung(maNguoiDung)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy MaNhanVien cho MaNguoiDung = " + maNguoiDung
                ));
    }
}
