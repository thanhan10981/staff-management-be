package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.mapper.NhanVienMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.NhanVienService;

import io.micrometer.core.ipc.http.HttpSender;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


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
        nv.setTrangThai(dto.getTrangThai());

        nv.setViTriCongViec(viTriRepo.findById(dto.getMaViTri()).orElse(null));
        nv.setPhongBan(phongBanRepo.findById(dto.getMaPhongBan()).orElse(null));
        nv.setKhoa(khoaRepo.findById(dto.getMaKhoa()).orElse(null));
        nv.setCccd(dto.getCccd());
        nv.setNgayVaoLam(dto.getNgayVaoLam());
        nv.setTrinhDoChuyenMon(dto.getTrinhDoChuyenMon());
        return mapper.toDto(
                repo.save(nv));
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
    @Transactional
    public void delete(Integer id) {
        donNghiPhepRepo.deleteByNhanVienId(id);
        qrChamCongRepo.deleteByNhanVienId(id);
        thongBaoRepo.deleteByNhanVienId(id);
        hopDongLaoDongRepo.deleteByNhanVienId(id);
        chungChiHanhNgheRepo.deleteByNhanVienId(id);
        tiemChungSucKhoeRepo.deleteByNhanVienId(id);
        luongThangRepo.deleteByNhanVienId(id);
        luongPhuCapRepo.deleteByNhanVienId(id);

        // XỬ LÝ User
        NguoiDung user = nguoiDungRepo.findByNhanVienId(id);
        if (user != null) {
            nguoiDungQuyenRepo.deleteByNguoiDungId(user.getMaNguoiDung());
            nguoiDungRepo.delete(user);
        }

        // Xóa nhân viên cuối cùng
        repo.deleteById(id);
    }


}
