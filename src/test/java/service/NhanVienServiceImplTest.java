package service;


import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.mapper.NhanVienMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.impl.NhanVienServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NhanVienServiceImplTest {

    @Mock private NhanVienRepository repo;
    @Mock private ViTriCongViecRepository viTriRepo;
    @Mock private PhongBanRepository phongBanRepo;
    @Mock private KhoaRepository khoaRepo;
    @Mock private NhanVienMapper mapper;
    @Mock private NguoiDungRepository nguoiDungRepo;
    @Mock private NguoiDungQuyenRepository nguoiDungQuyenRepo;
    @Mock private LichTrucNgayRepository lichTrucNgayRepo;
    @Mock private PhanCongCaTrucRepository phanCongCaTrucRepo;
    @Mock private LuongThangRepository luongThangRepo;
    @Mock private LuongPhuCapRepository luongPhuCapRepo;
    @Mock private ChungChiRepository chungChiRepo;
    @Mock private TiemChungRepository tiemChungRepo;
    @Mock private DonNghiPhepRepository donNghiPhepRepo;
    @Mock private QRChamCongRepository qrChamCongRepo;
    @Mock private ThongBaoRepository thongBaoRepo;
    @Mock private HopDongLaoDongRepository hopDongRepo;

    @InjectMocks
    private NhanVienServiceImpl service;

    // ===== TC1: getAll =====
    @Test
    void getAll_ok() {
        NhanVien nv = new NhanVien();
        NhanVienDTO dto = new NhanVienDTO();

        when(repo.findAll()).thenReturn(List.of(nv));
        when(mapper.toDto(nv)).thenReturn(dto);

        List<NhanVienDTO> result = service.getAll();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    // ===== TC2: getById =====
    @Test
    void getById_found() {
        NhanVien nv = new NhanVien();
        NhanVienDTO dto = new NhanVienDTO();

        when(repo.findById(1)).thenReturn(Optional.of(nv));
        when(mapper.toDto(nv)).thenReturn(dto);

        assertNotNull(service.getById(1));
    }

    // ===== TC3: update OK =====
    @Test
    void update_ok() {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(1);

        PhongBan pb = new PhongBan();
        ViTriCongViec vt = new ViTriCongViec();
        Khoa k = new Khoa();

        when(repo.findById(1)).thenReturn(Optional.of(nv));
        when(phongBanRepo.findById(1)).thenReturn(Optional.of(pb));
        when(viTriRepo.findById(1)).thenReturn(Optional.of(vt));
        when(khoaRepo.findById(1)).thenReturn(Optional.of(k));
        when(repo.save(any())).thenReturn(nv);

        NhanVienDTO req = new NhanVienDTO();
        req.setMaPhongBan(1);
        req.setMaViTri(1);
        req.setMaKhoa(1);

        NhanVienDTO result = service.update(1, req);

        assertNotNull(result);
        verify(repo).save(nv);
    }

    // ===== TC4: delete =====
    @Test
    void delete_ok() {
        NguoiDung user = new NguoiDung();
        user.setMaNguoiDung(10);

        when(nguoiDungRepo.findByNhanVienId(1)).thenReturn(user);

        service.delete(1);

        verify(donNghiPhepRepo).deleteByNhanVienId(1);
        verify(qrChamCongRepo).deleteByNhanVienId(1);
        verify(nguoiDungQuyenRepo).deleteByNguoiDungId(10);
        verify(repo).deleteById(1);
    }
}
