package service;

import com.example.staffmanagementsystem.dto.TiemChungDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.TiemChung;
import com.example.staffmanagementsystem.mapper.TiemChungMapper;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.TiemChungRepository;
import com.example.staffmanagementsystem.service.impl.TiemChungServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TiemChungServiceImplTest {

    @Mock
    private TiemChungRepository tiemChungRepository;

    @Mock
    private NhanVienRepository nhanVienRepository;

    @Mock
    private TiemChungMapper tiemChungMapper;

    @InjectMocks
    private TiemChungServiceImpl tiemChungService;

    // ===== TC1: getAll =====
    @Test
    void getAll_ok() {
        TiemChung entity = new TiemChung();
        TiemChungDTO dto = new TiemChungDTO();

        when(tiemChungRepository.findAll()).thenReturn(List.of(entity));
        when(tiemChungMapper.toDto(entity)).thenReturn(dto);

        List<TiemChungDTO> result = tiemChungService.getAll();

        assertEquals(1, result.size());
        verify(tiemChungRepository).findAll();
        verify(tiemChungMapper).toDto(entity);
    }

    // ===== TC2: getByNhanVien =====
    @Test
    void getByNhanVien_ok() {
        TiemChung entity = new TiemChung();
        TiemChungDTO dto = new TiemChungDTO();

        when(tiemChungRepository.findByNhanVien_MaNhanVien(1))
                .thenReturn(List.of(entity));
        when(tiemChungMapper.toDto(entity)).thenReturn(dto);

        List<TiemChungDTO> result = tiemChungService.getByNhanVien(1);

        assertEquals(1, result.size());
        verify(tiemChungRepository).findByNhanVien_MaNhanVien(1);
    }

    // ===== TC3: create - SUCCESS =====
    @Test
    void create_ok() {
        TiemChungDTO inputDto = TiemChungDTO.builder()
                .maNhanVien(1)
                .loai("COVID")
                .ngayTiem(LocalDate.of(2025, 1, 1))
                .build();

        NhanVien nv = new NhanVien();
        TiemChung entity = new TiemChung();
        TiemChung savedEntity = new TiemChung();
        TiemChungDTO outputDto = new TiemChungDTO();

        when(nhanVienRepository.findById(1)).thenReturn(Optional.of(nv));
        when(tiemChungMapper.toEntity(inputDto, nv)).thenReturn(entity);
        when(tiemChungRepository.save(entity)).thenReturn(savedEntity);
        when(tiemChungMapper.toDto(savedEntity)).thenReturn(outputDto);

        TiemChungDTO result = tiemChungService.create(inputDto);

        assertNotNull(result);
        verify(nhanVienRepository).findById(1);
        verify(tiemChungRepository).save(entity);
    }

    // ===== TC4: create - NHAN VIEN NOT FOUND =====
    @Test
    void create_nhanVienNotFound_throwException() {
        TiemChungDTO dto = TiemChungDTO.builder()
                .maNhanVien(99)
                .build();

        when(nhanVienRepository.findById(99))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> tiemChungService.create(dto));

        assertEquals("Không tìm thấy nhân viên", ex.getMessage());
        verify(tiemChungRepository, never()).save(any());
    }
}
