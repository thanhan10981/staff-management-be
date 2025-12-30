package service;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import com.example.staffmanagementsystem.repository.AuditLogRepository;
import com.example.staffmanagementsystem.service.impl.AuditLogServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class AuditLogServiceTest {
    @InjectMocks
    private AuditLogServiceImpl service;

    @Mock
    private AuditLogRepository repository;

    @Test
    void getAllLogs_shouldReturnList() {
        when(repository.findAllLogs()).thenReturn(List.of());

        List<AuditLogResponseDTO> result = service.getAllLogs();

        assertNotNull(result);
    }
}
