package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TongNghiKhongPhepController;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNghiKhongPhepService;
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
        controllers = TongNghiKhongPhepController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TongNghiKhongPhepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TongNghiKhongPhepService service;

    @Test
    void tongNghiKhongPhep_shouldReturnValue() throws Exception {

        TongNghiKhongPhepDTO dto =
                new TongNghiKhongPhepDTOTestImpl(
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 31),
                        5L
                );

        Mockito.when(service.tinhTongNghiKhongPhep(
                Mockito.any(LocalDate.class),
                Mockito.anyString(),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/tong-nghi-khong-phep")
                                .param("ngayChon", "2025-12-15")
                                .param("loai", "THANG_NAY")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoNghiKhongPhep").value(5))
                .andExpect(jsonPath("$.tuNgay").value("2025-12-01"))
                .andExpect(jsonPath("$.denNgay").value("2025-12-31"));
    }

    @Test
    void tongNghiKhongPhep_withoutOptionalFilters() throws Exception {

        Mockito.when(service.tinhTongNghiKhongPhep(
                Mockito.any(),
                Mockito.anyString(),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(
                new TongNghiKhongPhepDTOTestImpl(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31),
                        0L
                )
        );

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/tong-nghi-khong-phep")
                                .param("ngayChon", "2025-06-01")
                                .param("loai", "NAM_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoNghiKhongPhep").value(0));
    }
}
