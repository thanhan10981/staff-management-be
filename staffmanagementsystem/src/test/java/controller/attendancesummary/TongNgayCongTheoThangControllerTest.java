package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TongNgayCongTheoThangController;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongNgayCongTheoThangService;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TongNgayCongTheoThangController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TongNgayCongTheoThangControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TongNgayCongTheoThangService service;

    @Test
    void thongKeTheoNam_shouldReturn12Months() throws Exception {

        List<TongNgayCongTheoThangDTO> mockResult = List.of(
                new TongNgayCongTheoThangDTOTestImpl(1, 20L),
                new TongNgayCongTheoThangDTOTestImpl(2, 18L),
                new TongNgayCongTheoThangDTOTestImpl(3, 22L)
        );

        Mockito.when(service.thongKeTheoNam(
                Mockito.any(LocalDate.class),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(mockResult);

        mockMvc.perform(
                        get("/api/cham-cong/bieu-do/tong-ngay-cong-theo-thang")
                                .param("ngayChon", "2025-06-01")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].thang").value(1))
                .andExpect(jsonPath("$[0].tongNgayCong").value(20))
                .andExpect(jsonPath("$[2].thang").value(3));
    }

    @Test
    void thongKeTheoNam_withoutOptionalFilters() throws Exception {

        Mockito.when(service.thongKeTheoNam(
                Mockito.any(LocalDate.class),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(List.of());

        mockMvc.perform(
                        get("/api/cham-cong/bieu-do/tong-ngay-cong-theo-thang")
                                .param("ngayChon", "2025-01-01")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
