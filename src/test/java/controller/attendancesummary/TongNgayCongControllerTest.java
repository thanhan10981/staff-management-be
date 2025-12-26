package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TongNgayCongController;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNgayCongService;
import com.example.staffmanagementsystem.utils.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TongNgayCongController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TongNgayCongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TongNgayCongService tongNgayCongService;

    @Test
    void tinhTongNgayCong_thangNay_shouldReturnValue() throws Exception {

        TongNgayCongDTO dto =
                new TongNgayCongDTOTestImpl(
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 31),
                        220
                );

        Mockito.when(tongNgayCongService.tinhTongNgayCong(
                Mockito.any(LocalDate.class),
                Mockito.eq(LoaiThongKeNgayCong.THANG_NAY),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/tong-ngay-cong")
                                .param("ngayChon", "2025-12-15")
                                .param("loai", "THANG_NAY")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoNgayCong").value(220))
                .andExpect(jsonPath("$.tuNgay").value("2025-12-01"))
                .andExpect(jsonPath("$.denNgay").value("2025-12-31"));
    }

    @Test
    void tinhTongNgayCong_namNay_withoutOptionalFilters() throws Exception {

        TongNgayCongDTO dto =
                new TongNgayCongDTOTestImpl(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31),
                        2500
                );

        Mockito.when(tongNgayCongService.tinhTongNgayCong(
                Mockito.any(LocalDate.class),
                Mockito.eq(LoaiThongKeNgayCong.NAM_NAY),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/tong-ngay-cong")
                                .param("ngayChon", "2025-06-01")
                                .param("loai", "NAM_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoNgayCong").value(2500));
    }
}
