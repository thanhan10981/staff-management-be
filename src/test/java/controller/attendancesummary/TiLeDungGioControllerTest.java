package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TiLeDungGioController;
import com.example.staffmanagementsystem.dto.attendancesummary.TiLeDungGioDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TiLeDungGioService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = TiLeDungGioController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TiLeDungGioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TiLeDungGioService tiLeDungGioService;

    @Test
    void tiLeDungGio_thangNay_shouldReturnResult() throws Exception {

        TiLeDungGioDTO dto =
                new TiLeDungGioDTOTestImpl(
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 31),
                        18L,
                        20L
                );

        Mockito.when(tiLeDungGioService.tinhTiLeDungGio(
                Mockito.any(LocalDate.class),
                Mockito.eq("THANG_NAY"),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/ti-le-dung-gio")
                                .param("ngayChon", "2025-12-15")
                                .param("loai", "THANG_NAY")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soLanDungGio").value(18))
                .andExpect(jsonPath("$.tongSoLanDiLam").value(20))
                .andExpect(jsonPath("$.tiLeDungGio").value(90.0));
    }

    @Test
    void tiLeDungGio_withoutOptionalFilters_shouldReturnResult() throws Exception {

        TiLeDungGioDTO dto =
                new TiLeDungGioDTOTestImpl(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31),
                        0L,
                        0L
                );

        Mockito.when(tiLeDungGioService.tinhTiLeDungGio(
                Mockito.any(),
                Mockito.eq("NAM_NAY"),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/ti-le-dung-gio")
                                .param("ngayChon", "2025-06-01")
                                .param("loai", "NAM_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tiLeDungGio").value(0.0));
    }
}
