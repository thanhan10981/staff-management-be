package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;
import com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong.TongNgayCongTheoThangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TongNgayCongTheoThangServiceImpl
        implements TongNgayCongTheoThangService {

    private final TongNgayCongTheoThangRepository repository;

    @Override
    public List<TongNgayCongTheoThangDTO> thongKeTheoNam(
            LocalDate ngayChon,
            Integer maPhongBan,
            Integer maViTri
    ) {
        int nam = ngayChon.getYear();

        return repository.tongNgayCongTheoNam(
                nam,
                maPhongBan,
                maViTri
        );
    }
}
