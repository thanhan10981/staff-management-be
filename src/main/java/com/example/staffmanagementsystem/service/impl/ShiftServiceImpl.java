package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.dto.schedule.NhanVienScheduleDTO;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.mapper.LichTrucNgayMapper;
import com.example.staffmanagementsystem.mapper.ShiftMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.schedule.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {

    private final LichTrucNgayRepository lichRepo;
    private final CauHinhCaTruc_PhongRepository cauHinhRepo;
    private final NhanVienRepository nhanVienRepo;
    private final LichTrucNgayMapper mapper;


    @Override
    public List<LichTrucNgayDTO> getLichTrucByKhoa(Integer maKhoa, LocalDate from, LocalDate to) {

        return lichRepo.findByPhongVatLy_Khoa_IdAndNgayTrucBetween(maKhoa, from, to)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public LichTrucNgayDTO assignSingleShift(LichTrucNgayDTO dto) {

        var nv = nhanVienRepo.findById(dto.getMaNhanVien())
                .orElseThrow(() -> new IllegalArgumentException("Nhân viên không tồn tại"));

        LichTrucNgay e = LichTrucNgay.builder()
                .nhanVien(nv)
                .maCa(dto.getMaCa())
                .maPhong(dto.getMaPhong())
                .ngayTruc(dto.getNgayTruc())
                .trangThai(dto.getTrangThai())
                .ghiChu(dto.getGhiChu())
                .build();

        lichRepo.save(e);
        return mapper.toDTO(e);
    }

    @Override
    public List<LichTrucNgayDTO> createPhanCongAndGenerateLich(PhanCongCaTrucDTO dto) {

        Integer maNV = dto.getMaNhanVien();
        Integer maPhong = dto.getMaPhong();
        Integer maCa = dto.getMaCa();
        LocalDate start = dto.getNgayBatDau();
        LocalDate end = dto.getNgayKetThuc();

        var nv = nhanVienRepo.findById(maNV)
                .orElseThrow(() -> new IllegalArgumentException("NV không tồn tại"));

        List<LichTrucNgayDTO> result = new ArrayList<>();

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {

            LichTrucNgay e = LichTrucNgay.builder()
                    .nhanVien(nv)
                    .maCa(maCa)
                    .maPhong(maPhong)
                    .ngayTruc(d)
                    .trangThai("SCHEDULED")
                    .build();

            lichRepo.save(e);
            result.add(mapper.toDTO(e));
        }

        return result;
    }

    @Override
    public LichTrucNgayDTO getShiftById(Integer id) {
        return mapper.toDTO(
                lichRepo.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch"))
        );
    }

    @Override
    public LichTrucNgayDTO updateShift(Integer id, LichTrucNgayDTO dto) {

        LichTrucNgay e = lichRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch"));

        e.setMaCa(dto.getMaCa());
        e.setMaPhong(dto.getMaPhong());
        e.setNgayTruc(dto.getNgayTruc());
        e.setTrangThai(dto.getTrangThai());
        e.setGhiChu(dto.getGhiChu());

        lichRepo.save(e);
        return mapper.toDTO(e);
    }

    @Override
    public void deleteShift(Integer id) {
        if (!lichRepo.existsById(id))
            throw new IllegalArgumentException("Lịch không tồn tại");
        lichRepo.deleteById(id);
    }

    @Override
    public int deleteShiftsByPhanCong(Integer maPhanCong) {
        return 0; // chưa implement vì chưa có quan hệ
    }

    @Override
    public LichTrucNgayDTO updateShiftStatus(Integer id, String status) {
        LichTrucNgay e = lichRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy lịch"));
        e.setTrangThai(status);
        lichRepo.save(e);
        return mapper.toDTO(e);
    }

    @Override
    public Map<String, Long> getShiftStats(Integer maKhoa, LocalDate from, LocalDate to) {

        List<LichTrucNgay> list = lichRepo.findByPhongVatLy_Khoa_IdAndNgayTrucBetween(
                maKhoa, from, to
        );

        return list.stream()
                .collect(Collectors.groupingBy(
                        LichTrucNgay::getTrangThai,
                        Collectors.counting()
                ));
    }
    @Override
    public List<NhanVienScheduleDTO> getNhanVienTheoKhoaPhong(Integer maKhoa, Integer maPhongBan) {

        List<NhanVien> list;

        if (maPhongBan != null) {
            list = nhanVienRepo.findByKhoa_IdAndPhongBan_Id(maKhoa, maPhongBan);
        } else {
            list = nhanVienRepo.findByKhoa_Id(maKhoa);
        }

        return list.stream().map(this::toDTO).toList();
    }

    private NhanVienScheduleDTO toDTO(NhanVien nv) {
        return NhanVienScheduleDTO.builder()
                .maNhanVien(nv.getMaNhanVien())
                .tenNhanVien(nv.getTenNhanVien())

                .maKhoa(nv.getKhoa() != null ? nv.getKhoa().getId() : null)
                .tenKhoa(nv.getKhoa() != null ? nv.getKhoa().getTenKhoa() : null)

                .maPhongBan(nv.getPhongBan() != null ? nv.getPhongBan().getId() : null)
                .tenPhongBan(nv.getPhongBan() != null ? nv.getPhongBan().getTenPhongBan() : null)

                .build();
    }

}
