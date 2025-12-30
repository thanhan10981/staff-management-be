package service;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.mapper.NhanVienMapper;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.impl.NhanVienServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit test cho BÁO CÁO NHÂN SỰ (NhanVienService.getAll)
 */
@ExtendWith(MockitoExtension.class)
class NhanSuReportServiceTest {

    // ⚠️ TÊN PHẢI TRÙNG VỚI FIELD TRONG SERVICE
    @Mock
    private NhanVienRepository repo;

    @Mock
    private NhanVienMapper mapper;

    @InjectMocks
    private NhanVienServiceImpl nhanVienService;

    @Test
    void getAll_shouldReturnList() {
        // GIVEN
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(1);
        nv.setTenNhanVien("Nguyen Van A");

        NhanVienDTO dto = new NhanVienDTO();
        dto.setMaNhanVien(1);
        dto.setTenNhanVien("Nguyen Van A");

        when(repo.findAll()).thenReturn(List.of(nv));
        when(mapper.toDto(nv)).thenReturn(dto);

        // WHEN
        List<NhanVienDTO> result = nhanVienService.getAll();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Nguyen Van A", result.get(0).getTenNhanVien());
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        when(repo.findAll()).thenReturn(List.of());

        List<NhanVienDTO> result = nhanVienService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
