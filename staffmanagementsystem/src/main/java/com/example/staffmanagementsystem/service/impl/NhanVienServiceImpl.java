package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.mapper.NhanVienMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.NhanVienService;

import io.micrometer.core.ipc.http.HttpSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.getString;


@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienService {

    private final NhanVienRepository repo;
    private final ViTriCongViecRepository viTriRepo;
    private final PhongBanRepository phongBanRepo;
    private final KhoaRepository khoaRepo;
    private final NhanVienMapper mapper;
    private final NguoiDungRepository nguoiDungRepo;
    private final NguoiDungQuyenRepository nguoiDungQuyenRepo;
    private final LichTrucNgayRepository lichTrucNgayRepo;
    private final PhanCongCaTrucRepository phanCongCaTrucRepo;
    private final LuongThangRepository luongThangRepo;
    private final LuongPhuCapRepository luongPhuCapRepo;
    private final ChungChiRepository chungChiHanhNgheRepo;
    private final TiemChungRepository tiemChungSucKhoeRepo;
    private final DonNghiPhepRepository donNghiPhepRepo;
    private final QRChamCongRepository qrChamCongRepo;
    private final ThongBaoRepository thongBaoRepo;
    private final HopDongLaoDongRepository hopDongLaoDongRepo;
    @Override
    public List<NhanVienDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public NhanVienDTO getById(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public NhanVienDTO create(NhanVienDTO dto) {

        NhanVien nv = new NhanVien();

        nv.setTenNhanVien(dto.getTenNhanVien());
        nv.setNgaySinh(dto.getNgaySinh());
        nv.setGioiTinh(dto.getGioiTinh());
        nv.setEmail(dto.getEmail());
        nv.setSdt(dto.getSdt());
        nv.setTrangThai("Đang làm việc");

        if (dto.getMaViTri() != null) {
            nv.setViTriCongViec(viTriRepo.findById(dto.getMaViTri())
                    .orElseThrow(() -> new RuntimeException("Vị trí không tồn tại")));
        }

        if (dto.getMaPhongBan() != null) {
            nv.setPhongBan(phongBanRepo.findById(dto.getMaPhongBan())
                    .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại")));
        }

        if (dto.getMaKhoa() != null) {
            nv.setKhoa(khoaRepo.findById(dto.getMaKhoa())
                    .orElseThrow(() -> new RuntimeException("Khoa không tồn tại")));
        }


        nv.setCccd(dto.getCccd());
        nv.setNgayVaoLam(dto.getNgayVaoLam());
        nv.setTrinhDoChuyenMon(dto.getTrinhDoChuyenMon());

        nv.setLienHeKhanCap(dto.getLienHeKhanCap());
        nv.setSdtLienHeKhanCap(dto.getSdtLienHeKhanCap());

        nv.setAnhDaiDien(dto.getAnhDaiDien());
        nv.setHopDongFile(dto.getHopDongFile());

        return mapper.toDto(repo.save(nv));
    }


    @Override
    public NhanVienDTO update(Integer id, NhanVienDTO req) {

        // 1. Lấy entity cũ từ DB
        NhanVien entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("NhanVien not found"));

        // 2. Gán dữ liệu từ DTO sang Entity
        entity.setTenNhanVien(req.getTenNhanVien());
        entity.setEmail(req.getEmail());
        entity.setSdt(req.getSdt());
        entity.setTrangThai(req.getTrangThai());

        // Lấy Phòng ban
        PhongBan pb = phongBanRepo.findById(req.getMaPhongBan())
                .orElseThrow(() -> new RuntimeException("PhongBan not found"));
        entity.setPhongBan(pb);

        // Lấy Vị Trí
        ViTriCongViec vt = viTriRepo.findById(req.getMaViTri())
                .orElseThrow(() -> new RuntimeException("ViTri not found"));
        entity.setViTriCongViec(vt);

        // Lấy Khoa
        Khoa k = khoaRepo.findById(req.getMaKhoa())
                .orElseThrow(() -> new RuntimeException("Khoa not found"));
        entity.setKhoa(k);

        // 3. Lưu lại
        NhanVien saved = repo.save(entity);

        // 4. Convert Entity → DTO để trả về FE
        NhanVienDTO dto = new NhanVienDTO();

        dto.setMaNhanVien(saved.getMaNhanVien());
        dto.setTenNhanVien(saved.getTenNhanVien());
        dto.setNgaySinh(saved.getNgaySinh());
        dto.setGioiTinh(saved.getGioiTinh());
        dto.setSdt(saved.getSdt());
        dto.setEmail(saved.getEmail());
        dto.setTrangThai(saved.getTrangThai());

// Phòng ban
        dto.setMaPhongBan(saved.getPhongBan().getId());
        dto.setTenPhongBan(saved.getPhongBan().getTenPhongBan());

// Vị trí công việc
        dto.setMaViTri(saved.getViTriCongViec().getId());
        dto.setTenViTri(saved.getViTriCongViec().getTenViTri());

// Khoa
        dto.setMaKhoa(saved.getKhoa().getId());  // hoặc getMaKhoa()
        dto.setTenKhoa(saved.getKhoa().getTenKhoa());

// Thông tin khác
        dto.setCccd(saved.getCccd());
        dto.setNgayVaoLam(saved.getNgayVaoLam());
        dto.setTrinhDoChuyenMon(saved.getTrinhDoChuyenMon());
        dto.setLienHeKhanCap(saved.getLienHeKhanCap());
        dto.setSdtLienHeKhanCap(saved.getSdtLienHeKhanCap());
        dto.setAnhDaiDien(saved.getAnhDaiDien());
        dto.setHopDongFile(saved.getHopDongFile());

        return dto;

    }
    @Override
    public ResponseEntity<?> importExcel(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", 0, "fail", 0, "message", "File rỗng!")
            );
        }

        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            int success = 0, fail = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    NhanVien nv = new NhanVien();
                    nv.setTenNhanVien(getString(row, 0));
                    nv.setNgaySinh(getLocalDate(row, 1));
                    nv.setGioiTinh("Nam".equalsIgnoreCase(getString(row, 2)));
                    nv.setCccd(getString(row, 3));
                    nv.setEmail(getString(row, 4));
                    nv.setSdt(getString(row, 5));
                    nv.setNgayVaoLam(getLocalDate(row, 6));

                    Integer maViTri = getInteger(row, 7);
                    Integer maPhongBan = getInteger(row, 8);
                    Integer maKhoa = getInteger(row, 9);

                    if (maViTri != null)
                        nv.setViTriCongViec(viTriRepo.findById(maViTri).orElse(null));

                    if (maPhongBan != null)
                        nv.setPhongBan(phongBanRepo.findById(maPhongBan).orElse(null));

                    if (maKhoa != null)
                        nv.setKhoa(khoaRepo.findById(maKhoa).orElse(null));

                    nv.setLienHeKhanCap(getString(row, 10));
                    nv.setSdtLienHeKhanCap(getString(row, 11));

                    repo.save(nv);
                    success++;

                } catch (Exception e) {
                    fail++;
                }
            }

            workbook.close();

            return ResponseEntity.ok(
                    Map.of(
                            "success", success,
                            "fail", fail,
                            "message", "Import thành công"
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Lỗi xử lý file", "message", e.getMessage())
            );
        }
    }


    @Override
    @Transactional
    public void delete(Integer id) {

        // 1) XÓA TẤT CẢ BẢNG PHỤ LIÊN QUAN NHÂN VIÊN
        lichTrucNgayRepo.deleteByNhanVienId(id);
        phanCongCaTrucRepo.deleteByNhanVienId(id);
        donNghiPhepRepo.deleteByNhanVienId(id);
        qrChamCongRepo.deleteByNhanVienId(id);
        thongBaoRepo.deleteByNhanVienId(id);
        hopDongLaoDongRepo.deleteByNhanVienId(id);
        chungChiHanhNgheRepo.deleteByNhanVienId(id);
        tiemChungSucKhoeRepo.deleteByNhanVienId(id);
        luongThangRepo.deleteByNhanVienId(id);
        luongPhuCapRepo.deleteByNhanVienId(id);

        // 2) XỬ LÝ USER
        NguoiDung user = nguoiDungRepo.findByNhanVienId(id);

        if (user != null) {

            // 3) Xóa quyền
            nguoiDungQuyenRepo.deleteByNguoiDungId(user.getMaNguoiDung());

            // 5) Xóa NguoiDung
            nguoiDungRepo.delete(user);
        }

        // 6) Cuối cùng xóa NhanVien
        repo.deleteById(id);
    }
    private String getString(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return null;

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();

                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getLocalDateTimeCellValue().toLocalDate().toString();
                    }
                    return String.valueOf((long) cell.getNumericCellValue());

                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());

                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }



    private Integer getInteger(Row row, int index) {
        try {
            String v = getString(row, index);
            if (v == null || v.isBlank()) return null;
            return Integer.parseInt(v);
        } catch (Exception e) {
            return null;
        }
    }



    private LocalDate getLocalDate(Row row, int index) {
        try {
            Cell cell = row.getCell(index);
            if (cell == null) return null;

            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            }

            String str = getString(row, index);
            if (str == null) return null;

            return LocalDate.parse(str);
        } catch (Exception e) {
            return null;
        }
    }



}
