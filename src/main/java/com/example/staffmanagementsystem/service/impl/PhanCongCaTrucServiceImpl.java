package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.dto.schedule.PhanCongTheoDotDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.PhanCongCaTruc;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.mapper.PhanCongCaTrucMapper;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.PhanCongCaTrucRepository;
import com.example.staffmanagementsystem.service.PhanCongCaTrucService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhanCongCaTrucServiceImpl implements PhanCongCaTrucService {

    private final PhanCongCaTrucRepository repo;
    private final NhanVienRepository nvRepo;   // <- PHẢI CÓ DÒNG NÀY
    private final PhanCongCaTrucMapper mapper;
    private final LichTrucNgayRepository lichRepo;

    @Override
    public List<PhanCongCaTrucDTO> getByNhanVien(Integer maNV) {
        return repo.findByNhanVien_MaNhanVien(maNV)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PhanCongCaTrucDTO create(PhanCongCaTrucDTO dto) {

        NhanVien nv = nvRepo.findById(dto.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        PhanCongCaTruc pc = PhanCongCaTruc.builder()
                .nhanVien(nv)
                .maCa(dto.getMaCa())
                .maPhong(dto.getMaPhong())
                .maKhoa(dto.getMaKhoa())
                .ngayBatDau(dto.getNgayBatDau())
                .ngayKetThuc(dto.getNgayKetThuc())
                .lapLaiHangTuan(dto.getLapLaiHangTuan())
                .trangThai("1")
                .ghiChu(dto.getGhiChu())
                .build();

        repo.save(pc);
        return mapper.toDTO(pc);
    }

    @Override
    public PhanCongCaTrucDTO getById(Integer id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    @Override
    public List<PhanCongCaTrucDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional
    public List<LichTrucNgay> taoPhanCongVaSinhLich(PhanCongTheoDotDTO dto) {

        List<LichTrucNgay> result = new ArrayList<>();

        for (Integer maNV : dto.getDanhSachNhanVien()) {

            NhanVien nv = nvRepo.findById(maNV)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Không tồn tại nhân viên ID = " + maNV)
                    );

            // ============================
            // 1. TẠO PHÂN CÔNG
            // ============================
            PhanCongCaTruc pc = PhanCongCaTruc.builder()
                    .nhanVien(nv)
                    .maCa(dto.getMaCa())
                    .maPhong(dto.getMaPhong())
                    .maKhoa(dto.getMaKhoa())
                    .ngayBatDau(dto.getNgayBatDau())
                    .ngayKetThuc(dto.getNgayKetThuc())
                    .ghiChu(dto.getGhiChu())
                    .nguoiTao(dto.getNguoiTao())
                    .trangThai("ACTIVE")
                    .lapLaiHangTuan(0)
                    .build();

            repo.save(pc);

            // ============================
            // 2. SINH LỊCH NGÀY + CHECK TRÙNG
            // ============================
            LocalDate d = dto.getNgayBatDau();
            while (!d.isAfter(dto.getNgayKetThuc())) {

                // ✅ CHECK CONFLICT (QUAN TRỌNG)
                checkConflict(maNV, dto.getMaCa(), d);

                LichTrucNgay lich = LichTrucNgay.builder()
                        .nhanVien(nv)
                        .maCa(dto.getMaCa())
                        .maPhong(dto.getMaPhong())
                        .ngayTruc(d)
                        .trangThai("SCHEDULED")
                        .ghiChu(dto.getGhiChu())
                        .build();

                lichRepo.save(lich);
                result.add(lich);

                d = d.plusDays(1);
            }
        }

        return result;
    }


    private void checkConflict(
            Integer maNhanVien,
            Integer maCa,
            LocalDate ngayTruc
    ) {
        if (lichRepo.existsByNhanVien_MaNhanVienAndMaCaAndNgayTruc(
                maNhanVien, maCa, ngayTruc
        )) {

            LichTrucNgay existed = lichRepo
                    .findFirstByNhanVien_MaNhanVienAndMaCaAndNgayTruc(
                            maNhanVien, maCa, ngayTruc
                    )
                    .orElseThrow();

            String tenNV = existed.getNhanVien().getTenNhanVien();

            throw new IllegalArgumentException(
                    String.format(
                            "Nhân viên %s đã có ca %d vào ngày %s. Vui lòng chọn ca hoặc nhân viên khác.",
                            tenNV,
                            maCa,
                            ngayTruc
                    )
            );
        }
    }

}
