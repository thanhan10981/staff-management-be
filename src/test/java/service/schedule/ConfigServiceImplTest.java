package service.schedule;

import com.example.staffmanagementsystem.repository.CaLamViecRepository;
import com.example.staffmanagementsystem.repository.CauHinhCaTruc_PhongRepository;
import com.example.staffmanagementsystem.service.impl.ConfigServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigServiceImplTest {

    @Mock
    CaLamViecRepository caRepo;

    @Mock
    CauHinhCaTruc_PhongRepository cfgRepo;

    @InjectMocks
    ConfigServiceImpl service;

    @Test
    void getAllCa_empty() {
        when(caRepo.findAll()).thenReturn(List.of());

        assertTrue(service.getAllCa().isEmpty());
    }

    @Test
    void getCauHinh_notFound() {
        when(cfgRepo.findById(any())).thenReturn(Optional.empty());

        assertNull(service.getCauHinh(1, 1));
    }
}
