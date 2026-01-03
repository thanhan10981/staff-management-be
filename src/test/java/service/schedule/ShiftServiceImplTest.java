package service.schedule;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.mapper.LichTrucNgayMapper;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.impl.ShiftServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShiftServiceImplTest {

    @Mock
    LichTrucNgayRepository lichRepo;

    @Mock
    NhanVienRepository nhanVienRepo;

    @Mock
    LichTrucNgayMapper mapper;

    @InjectMocks
    ShiftServiceImpl service;

    @Test
    void SMS97_assignSingle_nvNotFound() {
        when(nhanVienRepo.findById(1)).thenReturn(Optional.empty());

        LichTrucNgayDTO dto = new LichTrucNgayDTO();
        dto.setMaNhanVien(1);

        assertThrows(IllegalArgumentException.class,
                () -> service.assignSingleShift(dto));
    }

    @Test
    void SMS99_delete_ok() {
        when(lichRepo.existsById(1)).thenReturn(true);

        service.deleteShift(1);

        verify(lichRepo).deleteById(1);
    }

    @Test
    void getShiftById_notFound() {
        when(lichRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.getShiftById(1));
    }

    @Test
    void updateShift_ok() {
        LichTrucNgay entity = new LichTrucNgay();
        when(lichRepo.findById(1)).thenReturn(Optional.of(entity));
        when(mapper.toDTO(any())).thenReturn(new LichTrucNgayDTO());

        LichTrucNgayDTO dto = new LichTrucNgayDTO();
        service.updateShift(1, dto);

        verify(lichRepo).save(entity);
    }

    @Test
    void updateShiftStatus_notFound() {
        when(lichRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> service.updateShiftStatus(1, "DONE"));
    }


}
