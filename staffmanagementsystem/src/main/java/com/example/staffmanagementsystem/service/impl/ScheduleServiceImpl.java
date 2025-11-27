package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.schedule.*;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.exception.BadRequestException;
import com.example.staffmanagementsystem.exception.NotFoundException;
import com.example.staffmanagementsystem.mapper.ScheduleMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.schedule.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final LichTrucNgayRepository lichRepo;
    private final PhanCongCaTrucRepository phanCongRepo;
    private final NhanVienRepository nvRepo;
    private final CaLamViecRepository caRepo;
    private final PhongVatLyRepository phongRepo;
    private final CauHinhCaTruc_PhongRepository cauHinhRepo;
    private final PhanQuyenRepository phanQuyenRepo;

    public ScheduleServiceImpl(LichTrucNgayRepository lichRepo,
                               PhanCongCaTrucRepository phanCongRepo,
                               NhanVienRepository nvRepo,
                               CaLamViecRepository caRepo,
                               PhongVatLyRepository phongRepo,
                               CauHinhCaTruc_PhongRepository cauHinhRepo,
                               PhanQuyenRepository phanQuyenRepo) {
        this.lichRepo = lichRepo;
        this.phanCongRepo = phanCongRepo;
        this.nvRepo = nvRepo;
        this.caRepo = caRepo;
        this.phongRepo = phongRepo;
        this.cauHinhRepo = cauHinhRepo;
        this.phanQuyenRepo = phanQuyenRepo;
    }

    @Override
    public List<DayScheduleDto> getSchedules(LocalDate start, LocalDate end, Integer phongId, Integer caId) {
        if (start == null || end == null) throw new BadRequestException("start/end required");
        List<LichTrucNgay> all = lichRepo.findByNgayTrucBetween(start, end);
        if (phongId != null) all = all.stream().filter(l -> phongId.equals(l.getMaPhong())).collect(Collectors.toList());
        if (caId != null) all = all.stream().filter(l -> caId.equals(l.getMaCa())).collect(Collectors.toList());

        Map<LocalDate, List<LichTrucNgay>> grouped = all.stream().collect(Collectors.groupingBy(LichTrucNgay::getNgayTruc));
        List<DayScheduleDto> out = new ArrayList<>();
        LocalDate cur = start;
        while (!cur.isAfter(end)) {
            List<LichTrucNgay> today = grouped.getOrDefault(cur, List.of());
            List<ShiftDto> shifts = today.stream().map(ScheduleMapper::toShiftDto).collect(Collectors.toList());
            out.add(new DayScheduleDto(cur, shifts));
            cur = cur.plusDays(1);
        }
        return out;
    }

    @Override
    public AssignmentResponse assignShift(AssignmentRequest req, Integer actorId) {

        if (req.getMaNhanVien() == null
                || req.getMaCa() == null
                || req.getMaPhong() == null
                || req.getNgayTruc() == null) {

            throw new BadRequestException("Missing fields");
        }

        LocalDate day = req.getNgayTruc();

        NhanVien nv = nvRepo.findById(req.getMaNhanVien())
                .orElseThrow(() -> new NotFoundException("NhanVien not found"));

        CaLamViec ca = caRepo.findById(req.getMaCa())
                .orElseThrow(() -> new NotFoundException("Ca not found"));

        PhongVatLy phong = phongRepo.findById(req.getMaPhong())
                .orElseThrow(() -> new NotFoundException("Phong not found"));

        // 1) CHECK CONFLICT
        List<LichTrucNgay> existed = lichRepo.findByEmpAndDate(nv.getMaNhanVien(), day);
        if (!existed.isEmpty()) {
            throw new BadRequestException("Nhân viên đã có lịch trực ngày này");
        }

        // 2) CREATE PHAN CONG
        PhanCongCaTruc p = new PhanCongCaTruc();
        p.setNhanVien(nv);
        p.setMaCa(ca.getMaCa());
        p.setMaPhong(phong.getMaPhong());
        p.setNgayBatDau(day);
        p.setNgayKetThuc(day);
        p.setLapLaiHangTuan(1);
        p.setNguoiTao(nv.getTenNhanVien());
        p.setTrangThai("Đã xếp");
        p.setGhiChu(req.getGhiChu());
        PhanCongCaTruc saved = phanCongRepo.save(p);

        // 3) CREATE LICH TRUC NGAY
        LichTrucNgay lich = new LichTrucNgay();
        lich.setNhanVien(nv);
        lich.setMaCa(ca.getMaCa());
        lich.setMaPhong(phong.getMaPhong());
        lich.setNgayTruc(day);
        lich.setTrangThai("Đã xếp");
        lich.setGhiChu(req.getGhiChu());

        lichRepo.save(lich);

        return AssignmentResponse.builder()
                .maPhanCong(saved.getMaPhanCong())
                .message("Tạo lịch trực thành công cho ngày " + day)
                .build();
    }


    @Override
    public void deleteAssignment(Integer assignmentId, Integer actorId) {
        PhanCongCaTruc ph = phanCongRepo.findById(assignmentId).orElseThrow(() -> new NotFoundException("Phân công không tồn tại"));
        // find and delete LichTrucNgay in date range for same employee/ca/phong
        List<LichTrucNgay> inRange = lichRepo.findByNgayTrucBetween(ph.getNgayBatDau(), ph.getNgayKetThuc());
        List<LichTrucNgay> toDelete = inRange.stream()
                .filter(l -> Objects.equals(l.getNhanVien(), ph.getNhanVien())
                        && Objects.equals(l.getMaCa(), ph.getMaCa())
                        && Objects.equals(l.getMaPhong(), ph.getMaPhong()))
                .collect(Collectors.toList());
        lichRepo.deleteAll(toDelete);
        phanCongRepo.delete(ph);
    }
}
