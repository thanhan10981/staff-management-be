package com.example.staffmanagementsystem.service.impl;
import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.dto.LichTrucTuanDTO;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.mapper.LichTrucNgayMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.LichTrucService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LichTrucServiceImpl implements LichTrucService {

    private final LichTrucNgayRepository lichRepo;
    private final CauHinhCaTruc_PhongRepository cauHinhRepo;
    private final NhanVienRepository nhanVienRepo;
    private final CaLamViecRepository caRepo;
    private final AuditLogRepository auditLogRepo;
    private final LichTrucNgayMapper mapper;

    public LichTrucServiceImpl(LichTrucNgayRepository lichRepo,
                               CauHinhCaTruc_PhongRepository cauHinhRepo,
                               NhanVienRepository nhanVienRepo,
                               CaLamViecRepository caRepo,
                               AuditLogRepository auditLogRepo,
                               LichTrucNgayMapper mapper) {
        this.lichRepo = lichRepo;
        this.cauHinhRepo = cauHinhRepo;
        this.nhanVienRepo = nhanVienRepo;
        this.caRepo = caRepo;
        this.auditLogRepo = auditLogRepo;
        this.mapper = mapper;
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoNgay(LocalDate date) {
        return lichRepo.findByNgayTruc(date).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoThang(Integer maKhoa, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = YearMonth.of(year, month).atEndOfMonth();
        return lichRepo.findByNgayTrucBetween(start, end)
                .stream()
                .filter(e -> {
                    if (e.getNhanVien() == null || e.getNhanVien().getKhoa() == null) return false;
                    Integer khoaId = e.getNhanVien().getKhoa().getId();
                    return Objects.equals(khoaId, maKhoa);
                })
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoPhong(Integer maPhong, LocalDate from, LocalDate to) {
        return lichRepo.findByMaPhongAndNgayTrucBetween(maPhong, from, to).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoNhanVien(Integer maNV) {
        return lichRepo.findByNhanVien_MaNhanVien(maNV).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> checkThieuNhanSu(Integer maPhong, Integer maCa, LocalDate date) {
        CauHinhCaTruc_Phong cfg = cauHinhRepo.findById(new CauHinhId(maPhong, maCa)).orElse(null);
        if (cfg == null) {
            return Map.of("missing", false, "message", "Không có cấu hình cho phòng/ca này");
        }

        List<LichTrucNgay> list = lichRepo.findByMaPhongAndMaCaAndNgayTruc(maPhong, maCa, date);

        // determine role via Nhân viên -> ViTriCongViec.tenViTri
        long bs = list.stream().filter(l -> isRole(l, "bac", "bacsi", "bác")).count();
        long yt = list.stream().filter(l -> isRole(l, "yta", "y tá", "yta")).count();
        long dd = list.stream().filter(l -> isRole(l, "dieu", "dieu duong", "điều")).count();
        long khac = list.size() - (bs + yt + dd);

        boolean thieu = (bs < nullSafe(cfg.getSoBacSi()))
                || (yt < nullSafe(cfg.getSoYTa()))
                || (dd < nullSafe(cfg.getSoDieuDuong()))
                || (khac < nullSafe(cfg.getSoNhanVienKhac()));

        Map<String, Integer> required = Map.of(
                "bacSi", nullSafe(cfg.getSoBacSi()),
                "yTa", nullSafe(cfg.getSoYTa()),
                "dieuDuong", nullSafe(cfg.getSoDieuDuong()),
                "nhanVienKhac", nullSafe(cfg.getSoNhanVienKhac())
        );
        Map<String, Long> current = Map.of(
                "bacSi", bs,
                "yTa", yt,
                "dieuDuong", dd,
                "nhanVienKhac", khac
        );

        return Map.of(
                "required", required,
                "current", current,
                "missing", thieu
        );
    }

    private int nullSafe(Integer v) { return v != null ? v : 0; }

    private boolean isRole(LichTrucNgay l, String... keywords) {
        if (l == null || l.getNhanVien() == null) return false;
        ViTriCongViec vt = l.getNhanVien().getViTriCongViec();
        if (vt == null || vt.getTenViTri() == null) return false;
        String t = vt.getTenViTri().toLowerCase();
        for (String k : keywords) {
            if (t.contains(k.toLowerCase())) return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void taoPhanCongTuan(Integer maNV, Integer maPhong, Integer maKhoa, LocalDate start, Integer actorId) {
        // tạo lịch cho 7 ngày liên tiếp (start..start+6)
        NhanVien nv = nhanVienRepo.findById(maNV).orElseThrow(() -> new IllegalArgumentException("Nhan vien not found"));
        if (nv.getKhoa() == null || !Objects.equals(nv.getKhoa().getId(), maKhoa)) {
            // optional: allow or throw, choose to continue but log
        }

        for (int i = 0; i < 7; ++i) {
            LocalDate d = start.plusDays(i);
            // Skip if already assigned same employee at same ca+room? We don't have ca param here, so just create basic
            LichTrucNgay newL = LichTrucNgay.builder()
                    .nhanVien(nv)
                    .maPhong(maPhong)
                    .ngayTruc(d)
                    .trangThai("CONFIRMED")
                    .build();
            lichRepo.save(newL);
        }

        // audit
        if (auditLogRepo != null) {
            AuditLog log = AuditLog.builder()
                    .nguoiThucHien(actorId)
                    .hanhDong("TAO_PHANCONG_TUAN")
                    .thoiGian(java.time.LocalDateTime.now())
                    .moTa("Tạo phân công tuần cho NV=" + maNV + " phòng=" + maPhong + " start=" + start)
                    .maNhanVien(maNV)
                    .build();
            auditLogRepo.save(log);
        }
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoTuan(Integer maNV, LocalDate start, LocalDate end) {
        // inclusive
        List<LichTrucNgay> list = lichRepo.findByNhanVien_MaNhanVienAndNgayTrucBetween(maNV, start, end);
        return list.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public LichTrucNgayDTO getShiftById(Integer id) {
        LichTrucNgay e = lichRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lịch trực không tồn tại"));
        return mapper.toDTO(e);
    }

    @Override
    @Transactional
    public LichTrucNgayDTO updateShift(Integer id, LichTrucNgayDTO dto) {
        LichTrucNgay existing = lichRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lịch trực không tồn tại"));

        if (dto.getMaNhanVien() != null) {
            NhanVien nv = nhanVienRepo.findById(dto.getMaNhanVien()).orElseThrow(() -> new IllegalArgumentException("NhanVien not found"));
            existing.setNhanVien(nv);
        }
        if (dto.getMaCa() != null) existing.setMaCa(dto.getMaCa());
        if (dto.getMaPhong() != null) existing.setMaPhong(dto.getMaPhong());
        if (dto.getNgayTruc() != null) existing.setNgayTruc(dto.getNgayTruc());
        existing.setTrangThai(dto.getTrangThai());
        existing.setGhiChu(dto.getGhiChu());

        LichTrucNgay saved = lichRepo.save(existing);

        if (auditLogRepo != null) {
            AuditLog log = AuditLog.builder()
                    .nguoiThucHien(null)
                    .hanhDong("UPDATE_SHIFT")
                    .thoiGian(java.time.LocalDateTime.now())
                    .moTa("Cập nhật lịch trực id=" + saved.getMaLichTruc())
                    .maNhanVien(saved.getMaNhanVien())
                    .build();
            auditLogRepo.save(log);
        }

        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteShift(Integer id) {
        LichTrucNgay existing = lichRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lịch trực không tồn tại"));
        lichRepo.delete(existing);

        if (auditLogRepo != null) {
            AuditLog log = AuditLog.builder()
                    .nguoiThucHien(null)
                    .hanhDong("DELETE_SHIFT")
                    .thoiGian(java.time.LocalDateTime.now())
                    .moTa("Xóa lịch trực id=" + id)
                    .maNhanVien(existing.getMaNhanVien())
                    .build();
            auditLogRepo.save(log);
        }
    }

    @Override
    public Map<Integer, Long> getEmployeeCountByNgay(Integer maKhoa, LocalDate date) {

        List<Map<String, Object>> raw =
                lichRepo.countByNgayAndKhoa(maKhoa, date);

        Map<Integer, Long> result = new LinkedHashMap<>();

        for (Map<String, Object> row : raw) {
            Integer ca = (Integer) row.get("ca");
            Long count = (Long) row.get("soNguoi");
            result.put(ca, count);
        }

        return result;
    }

    @Override
    public long tinhCaThieuNguoi(List<LichTrucNgayDTO> shifts) {

        Map<String, List<LichTrucNgayDTO>> grouped =
                shifts.stream()
                        .collect(Collectors.groupingBy(s ->
                                s.getNgayTruc() + "_" + s.getMaPhong() + "_" + s.getMaCa()
                        ));

        long count = 0;

        for (var entry : grouped.entrySet()) {
            List<LichTrucNgayDTO> ca = entry.getValue();

            int maPhong = ca.get(0).getMaPhong();
            int maCa = ca.get(0).getMaCa();

            // Lấy config yêu cầu từ DB
            var config = getCauHinhPhongCa(maPhong, maCa);
            if (config == null) continue;

            long soThucTe = ca.size();
            long soYeuCau =
                    config.getSoBacSi() +
                            config.getSoYTa() +
                            config.getSoDieuDuong() +
                            config.getSoNhanVienKhac();

            if (soThucTe < soYeuCau) {
                count++;
            }
        }

        return count;
    }

    private CauHinhCaTruc_Phong getCauHinhPhongCa(Integer maPhong, Integer maCa) {
        return cauHinhRepo.findById(new CauHinhId(maPhong, maCa)).orElse(null);
    }

    @Override
    public long tinhCaXungDot(List<LichTrucNgayDTO> shifts) {

        Map<String, List<LichTrucNgayDTO>> grouped =
                shifts.stream()
                        .collect(Collectors.groupingBy(s ->
                                s.getNgayTruc() + "_" + s.getMaNhanVien()
                        ));

        return grouped.values().stream()
                .filter(list -> list.stream().map(LichTrucNgayDTO::getMaCa).distinct().count() > 1)
                .count();
    }

    @Override
    public List<LichTrucTuanDTO> getBangLichTuanTheoKhoa(
            Integer maKhoa,
            LocalDate start,
            LocalDate end
    ) {

        // ✅ KHAI BÁO BIẾN list
        List<LichTrucNgay> list;

        // ✅ nếu maKhoa = null hoặc = 0 → lấy tất cả
        if (maKhoa == null || maKhoa == 0) {
            list = lichRepo.findByNgayTrucBetween(start, end);
        } else {
            list = lichRepo.findByNhanVien_Khoa_IdAndNgayTrucBetween(
                    maKhoa, start, end
            );
        }

        Map<Integer, LichTrucTuanDTO> map = new LinkedHashMap<>();

        for (LichTrucNgay l : list) {

            if (l.getNhanVien() == null) continue;

            Integer maNV = l.getNhanVien().getMaNhanVien();

            map.putIfAbsent(
                    maNV,
                    LichTrucTuanDTO.builder()
                            .maNhanVien(maNV)
                            .hoTen(l.getNhanVien().getTenNhanVien())
                            .tenPhong(
                                    l.getPhongVatLy() != null
                                            ? l.getPhongVatLy().getTenPhong()
                                            : null
                            )
                            .lichTheoNgay(new HashMap<>())
                            .build()
            );

            map.get(maNV)
                    .getLichTheoNgay()
                    .put(
                            l.getNgayTruc(),
                            l.getCaLamViec() != null
                                    ? l.getCaLamViec().getTenCa()
                                    : l.getTrangThai()
                    );
        }

        return new ArrayList<>(map.values());
    }

    @Override
    public List<LichTrucTuanDTO> getBangLichTuanTheoPhong(
            Integer maPhong,
            LocalDate start,
            LocalDate end
    ) {

        List<LichTrucNgay> list =
                lichRepo.findByMaPhongAndNgayTrucBetween(
                        maPhong, start, end
                );

        Map<Integer, LichTrucTuanDTO> map = new LinkedHashMap<>();

        for (LichTrucNgay l : list) {

            if (l.getNhanVien() == null) continue;

            Integer maNV = l.getMaNhanVien();

            map.putIfAbsent(
                    maNV,
                    LichTrucTuanDTO.builder()
                            .maNhanVien(maNV)
                            .hoTen(l.getNhanVien().getTenNhanVien())
                            .tenPhong(
                                    l.getPhongVatLy() != null
                                            ? l.getPhongVatLy().getTenPhong()
                                            : null
                            )
                            .lichTheoNgay(new HashMap<>())
                            .build()
            );

            map.get(maNV)
                    .getLichTheoNgay()
                    .put(
                            l.getNgayTruc(),
                            l.getCaLamViec() != null
                                    ? l.getCaLamViec().getTenCa()
                                    : l.getTrangThai()
                    );
        }

        return new ArrayList<>(map.values());
    }


}