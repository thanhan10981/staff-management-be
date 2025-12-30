package service;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveReportExportRepository;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveReportExportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

class LeaveReportExportServiceImplTest {

    @InjectMocks
    private LeaveReportExportServiceImpl service;

    @Mock
    private LeaveReportExportRepository repository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void exportLeaveReport_THANG_NAY_shouldCallRepo() {

        LeaveExportRequest request = new LeaveExportRequest();
        request.setTimeRange(LoaiThongKeNgayCong.THANG_NAY);
        request.setMaPhongBan(1);

        Mockito.when(repository.getLeaveSummaryRaw(any(), any(), any(), any()))
                .thenReturn(List.of());

        Mockito.when(repository.getLeaveDetailRaw(any(), any(), any(), any()))
                .thenReturn(List.of());

        byte[] result = service.exportLeaveReport(request);

        assertThat(result).isNotNull();

        Mockito.verify(repository, times(1))
                .getLeaveSummaryRaw(any(LocalDate.class), any(LocalDate.class), eq(1), isNull());

        Mockito.verify(repository, times(1))
                .getLeaveDetailRaw(any(LocalDate.class), any(LocalDate.class), eq(1), isNull());
    }
}
