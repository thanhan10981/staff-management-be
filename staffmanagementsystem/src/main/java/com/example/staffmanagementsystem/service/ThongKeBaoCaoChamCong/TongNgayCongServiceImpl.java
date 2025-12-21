package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;



import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;
import com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong.TongNgayCongRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class TongNgayCongServiceImpl implements TongNgayCongService {

    private final TongNgayCongRepository repository;

    public TongNgayCongServiceImpl(TongNgayCongRepository repository) {
        this.repository = repository;
    }

    @Override
    public TongNgayCongDTO tinhTongNgayCong(
            LocalDate ngayChon,
            LoaiThongKeNgayCong loai,
            Integer maPhongBan,
            Integer maViTri
    ) {
        LocalDate tuNgay;
        LocalDate denNgay;

        switch (loai) {
            case THANG_NAY -> {
                tuNgay = ngayChon.withDayOfMonth(1);
                denNgay = ngayChon.with(TemporalAdjusters.lastDayOfMonth());
            }
            case THANG_TRUOC -> {
                LocalDate thangTruoc = ngayChon.minusMonths(1);
                tuNgay = thangTruoc.withDayOfMonth(1);
                denNgay = thangTruoc.with(TemporalAdjusters.lastDayOfMonth());
            }
            case QUY_NAY -> {
                int quy = (ngayChon.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(ngayChon.getYear(), quy * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2)
                        .with(TemporalAdjusters.lastDayOfMonth());
            }
            case QUY_TRUOC -> {
                LocalDate qTruoc = ngayChon.minusMonths(3);
                int quy = (qTruoc.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(qTruoc.getYear(), quy * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2)
                        .with(TemporalAdjusters.lastDayOfMonth());
            }
            case NAM_NAY -> {
                tuNgay = LocalDate.of(ngayChon.getYear(), 1, 1);
                denNgay = LocalDate.of(ngayChon.getYear(), 12, 31);
            }
            case NAM_TRUOC -> {
                int nam = ngayChon.getYear() - 1;
                tuNgay = LocalDate.of(nam, 1, 1);
                denNgay = LocalDate.of(nam, 12, 31);
            }
            default -> throw new IllegalArgumentException("Loại thống kê không hợp lệ");
        }

        return repository.tinhTongNgayCong(
                tuNgay,
                denNgay,
                maPhongBan,
                maViTri
        );
    }

}
