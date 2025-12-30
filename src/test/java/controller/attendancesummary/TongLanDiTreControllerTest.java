package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TongLanDiTreController;
import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TongLanDiTreService;
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
        controllers = TongLanDiTreController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TongLanDiTreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TongLanDiTreService tongLanDiTreService;

    @Test
    void tongLanDiTre_thangNay_shouldReturnValue() throws Exception {

        TongLanDiTreDTO dto =
                new TongLanDiTreDTOTestImpl(
                        LocalDate.of(2025, 12, 1),
                        LocalDate.of(2025, 12, 31),
                        12L
                );

        Mockito.when(tongLanDiTreService.tinhTongLanDiTre(
                Mockito.any(LocalDate.class),
                Mockito.eq("THANG_NAY"),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/tong-lan-di-tre")
                                .param("ngayChon", "2025-12-10")
                                .param("loai", "THANG_NAY")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoLanDiTre").value(12))
                .andExpect(jsonPath("$.tuNgay").value("2025-12-01"))
                .andExpect(jsonPath("$.denNgay").value("2025-12-31"));
    }

    @Test
    void tongLanDiTre_withoutOptionalFilters_shouldReturnValue() throws Exception {

        TongLanDiTreDTO dto =
                new TongLanDiTreDTOTestImpl(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31),
                        0L
                );

        Mockito.when(tongLanDiTreService.tinhTongLanDiTre(
                Mockito.any(),
                Mockito.eq("NAM_NAY"),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(dto);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/tong-lan-di-tre")
                                .param("ngayChon", "2025-06-01")
                                .param("loai", "NAM_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongSoLanDiTre").value(0));
    }
}
