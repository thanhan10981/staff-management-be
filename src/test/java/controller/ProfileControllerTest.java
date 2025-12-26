package controller;


import com.example.staffmanagementsystem.controller.profile.ProfileController;
import com.example.staffmanagementsystem.dto.profile.NhanVienTomTatDto;


import com.example.staffmanagementsystem.service.profile.ProfileService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@Import(ProfileControllerTest.TestConfig.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileService profileService;

    @Test
    void getThongTinTomTat_shouldReturn200() throws Exception {
        when(profileService.getThongTinTomTatNhanVienHienTai())
                .thenReturn(
                        new NhanVienTomTatDto(
                                "Tùng", "mail", "a.jpg", "Bác sĩ"
                        )
                );

        mockMvc.perform(get("/api/profile/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenNhanVien").value("Tùng"));
    }

    @Test
    void doiMatKhau_shouldReturnMessage() throws Exception {
        mockMvc.perform(
                        put("/api/profile/doi-mat-khau")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "matKhauHienTai":"Old@12345",
                              "matKhauMoi":"New@123456",
                              "xacNhanMatKhau":"New@123456"
                            }
                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Đổi mật khẩu thành công"));
    }

    // 👇 CONFIG MOCK
    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {

        @org.springframework.context.annotation.Bean
        ProfileService profileService() {
            return org.mockito.Mockito.mock(ProfileService.class);
        }
    }
}
