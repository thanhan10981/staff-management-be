package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.ChamCongRealtimeDTO;
import com.example.staffmanagementsystem.dto.ChamCongTodayDTO;
import com.example.staffmanagementsystem.dto.QRCheckinRequest;
import com.example.staffmanagementsystem.dto.QRCreateResponse;
import com.example.staffmanagementsystem.entity.ChamCong;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.QRChamCong;
import com.example.staffmanagementsystem.repository.ChamCongRepository;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import com.example.staffmanagementsystem.repository.QRChamCongRepository;
import com.example.staffmanagementsystem.service.common.CurrentNhanVienService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChamCongService {

    private final ChamCongRepository chamCongRepository;
    private final QRChamCongRepository qrChamCongRepository;
    private final CurrentNhanVienService currentNhanVienService;
    private final LichTrucNgayRepository lichTrucNgayRepository;
    private final EntityManager entityManager;

    public ChamCongTodayDTO getChamCongToday() {
        Integer maNV = currentNhanVienService.getMaNhanVien();
        LocalDate today = LocalDate.now();

        return chamCongRepository
                .findByMaNVAndLichTrucNgay_NgayTruc(maNV.toString(), today)
                .map(cc -> new ChamCongTodayDTO(
                        cc.getThoiGianVao() != null ? cc.getThoiGianVao().toLocalTime().toString() : null,
                        cc.getThoiGianRa() != null ? cc.getThoiGianRa().toLocalTime().toString() : null,
                        cc.getTrangThai()
                ))
                .orElse(new ChamCongTodayDTO(null, null, "CHUA_CHAM"));
    }

    @Transactional
    public String chamCongBangNut(String thietBi) {
        Integer maNV = currentNhanVienService.getMaNhanVien();
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        LichTrucNgay lichTruc = lichTrucNgayRepository
                .findByNhanVien_MaNhanVienAndNgayTruc(maNV, today)
                .orElseThrow(() -> new RuntimeException("Không có lịch trực hôm nay"));

        ChamCong chamCong = chamCongRepository
                .findByMaNVAndLichTrucNgay_NgayTruc(maNV.toString(), today)
                .orElse(ChamCong.builder()
                        .maNV(maNV.toString())
                        .lichTrucNgay(lichTruc)
                        .trangThai("CHUA_CHAM")
                        .build());

        if (chamCong.getThoiGianVao() == null) {
            chamCong.setThoiGianVao(now);
            chamCong.setTrangThai("DANG_LAM");
            chamCong.setThietBi(thietBi);
            chamCongRepository.save(chamCong);
            return "Giờ vào đã được ghi nhận lúc " + now.toLocalTime();
        }

        if (chamCong.getThoiGianRa() == null) {
            chamCong.setThoiGianRa(now);
            chamCong.setTrangThai("HOAN_TAT");
            chamCongRepository.save(chamCong);
            return "Giờ ra đã được ghi nhận lúc " + now.toLocalTime();
        }

        throw new RuntimeException("Ca làm đã hoàn tất");
    }

    @Transactional
    public ChamCongRealtimeDTO chamCongBangQR(QRCheckinRequest request) {

        QRChamCong qr = qrChamCongRepository
                .findByMaQRCodeAndTrangThai(request.getMaQRCode(), "ACTIVE")
                .orElseThrow(() -> new RuntimeException("QR không hợp lệ hoặc đã hết hạn"));

        if (!qr.getNgayTao().equals(LocalDate.now())) {
            throw new RuntimeException("QR đã hết hạn");
        }

        Integer maNV = qr.getNhanVien().getMaNhanVien();
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        LichTrucNgay lichTruc = lichTrucNgayRepository
                .findByNhanVien_MaNhanVienAndNgayTruc(maNV, today)
                .orElseThrow(() -> new RuntimeException("Không có lịch trực hôm nay"));

        ChamCong chamCong = chamCongRepository
                .findByMaNVAndLichTrucNgay_NgayTruc(maNV.toString(), today)
                .orElse(ChamCong.builder()
                        .maNV(maNV.toString())
                        .lichTrucNgay(lichTruc)
                        .qrChamCong(qr)
                        .build());

        if (chamCong.getThoiGianVao() == null) {
            chamCong.setThoiGianVao(now);
            chamCong.setTrangThai("DANG_LAM");
        } else if (chamCong.getThoiGianRa() == null) {
            chamCong.setThoiGianRa(now);
            chamCong.setTrangThai("HOAN_TAT");
        } else {
            throw new RuntimeException("Đã hoàn tất ca làm");
        }

        chamCong.setThietBi(request.getThietBi());
        chamCongRepository.save(chamCong);

        return new ChamCongRealtimeDTO(
                now.toLocalTime().toString(),
                chamCong.getTrangThai(),
                request.getThietBi()
        );
    }
    @Transactional
    public QRCreateResponse taoQRChamCong() {
        Integer maNV = currentNhanVienService.getMaNhanVien();

        NhanVien nhanVienRef = entityManager.getReference(NhanVien.class, maNV);

        QRChamCong qr = QRChamCong.builder()
                .maQRCode(UUID.randomUUID().toString())
                .ngayTao(LocalDate.now())
                .trangThai("ACTIVE")
                .nhanVien(nhanVienRef)
                .build();

        qrChamCongRepository.save(qr);

        return new QRCreateResponse(
                qr.getMaQRCode(),
                LocalDateTime.now().plusMinutes(5)
        );
    }


}


