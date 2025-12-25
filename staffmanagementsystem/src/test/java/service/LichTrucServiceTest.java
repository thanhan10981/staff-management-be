package service;

import com.example.staffmanagementsystem.dto.LichTrucTuanDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import com.example.staffmanagementsystem.service.impl.LichTrucServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LichTrucServiceTest {

    @Mock
    private LichTrucNgayRepository lichRepo;

    @InjectMocks
    private LichTrucServiceImpl lichTrucService;

    // ===== TEST 1: Lịch tuần theo khoa =====
    @Test
    void getBangLichTuanTheoKhoa_shouldReturnData() {

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(1);
        nv.setTenNhanVien("Nguyen Van A");

        LichTrucNgay lich = new LichTrucNgay();
        lich.setNhanVien(nv);
        lich.setNgayTruc(LocalDate.now());

        when(lichRepo.findByNhanVien_Khoa_IdAndNgayTrucBetween(
                anyInt(), any(), any()
        )).thenReturn(List.of(lich));

        List<LichTrucTuanDTO> result =
                lichTrucService.getBangLichTuanTheoKhoa(
                        1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(6)
                );

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    // ===== TEST 2: Lịch tuần theo phòng =====
    @Test
    void getBangLichTuanTheoPhong_shouldReturnData() {

        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(2);
        nv.setTenNhanVien("Tran Thi B");

        LichTrucNgay lich = new LichTrucNgay();
        lich.setNhanVien(nv);
        lich.setMaPhong(10);
        lich.setNgayTruc(LocalDate.now());

        when(lichRepo.findByMaPhongAndNgayTrucBetween(
                anyInt(), any(), any()
        )).thenReturn(List.of(lich));

        List<LichTrucTuanDTO> result =
                lichTrucService.getBangLichTuanTheoPhong(
                        10,
                        LocalDate.now(),
                        LocalDate.now().plusDays(6)
                );

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
