package service;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewDTO;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewFilterRequest;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveOverviewRepository;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveOverviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;

@ExtendWith(MockitoExtension.class)
class LeaveOverviewServiceImplTest {

    @InjectMocks
    private LeaveOverviewServiceImpl service;

    @Mock
    private LeaveOverviewRepository repository;

    @Test
    void getLeaveOverview_THANG_NAY_success() {
        // given
        LeaveOverviewFilterRequest request =
                LeaveOverviewFilterRequest.builder()
                        .timeRange(LoaiThongKeNgayCong.THANG_NAY)
                        .maPhongBan(null)
                        .tenPhongBan(null)
                        .build();

        List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{
                "avatar.png",
                "Nguyen Van A",
                "a@gmail.com",
                "IT",
                2,
                1,
                0,
                3,
                9
        });


        when(repository.getLeaveOverviewRaw(
                any(), any(), any(), any()
        )).thenReturn(mockData);

        // when
        List<LeaveOverviewDTO> result = service.getLeaveOverview(request);

        // then
        assertEquals(1, result.size());
        LeaveOverviewDTO dto = result.get(0);

        assertEquals("Nguyen Van A", dto.getTenNhanVien());
        assertEquals(3, dto.getTongNgayNghi());
        assertEquals(9, dto.getSoNgayConLai());

        verify(repository, times(1))
                .getLeaveOverviewRaw(any(), any(), any(), any());
    }
}
