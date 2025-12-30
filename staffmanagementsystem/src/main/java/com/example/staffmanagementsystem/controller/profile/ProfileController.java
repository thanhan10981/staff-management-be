package com.example.staffmanagementsystem.controller.profile;

import com.example.staffmanagementsystem.dto.profile.*;
import com.example.staffmanagementsystem.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/summary")
    public NhanVienTomTatDto getThongTinTomTat() {
        return profileService.getThongTinTomTatNhanVienHienTai();
    }

    @GetMapping("/me")
    public ThongTinCaNhanDto getThongTinCaNhan() {
        return profileService.getThongTinCaNhan();
    }

    @GetMapping("/lien-he-cong-viec")
    public ThongTinLienHeCongViecDto getThongTinLienHeCongViec() {
        return profileService.getThongTinLienHeCongViec();
    }

    @GetMapping("/me/overview")
    public ResponseEntity<?> getThongTinTongQuan() {
        return ResponseEntity.ok(
                profileService.getThongTinTongQuanNhanVien()
        );
    }

    @PutMapping("/thong-tin-ca-nhan")
    public ResponseEntity<ThongTinNhanVienFormDto> capNhatThongTinCaNhan(
            @RequestBody CapNhatThongTinNhanVienDto dto
    ) {
        return ResponseEntity.ok(
                profileService.capNhatThongTinCaNhan(dto)
        );
    }

    @GetMapping("/me/form")
    public ResponseEntity<ThongTinNhanVienFormDto> getThongTinCaNhanForm() {
        return ResponseEntity.ok(
                profileService.getThongTinCaNhanForm()
        );
    }


    @PutMapping("/doi-mat-khau")
    public ResponseEntity<?> doiMatKhau(
            @RequestBody DoiMatKhauDto dto
    ) {
        profileService.doiMatKhau(dto);
        return ResponseEntity.ok(Map.of("message", "Đổi mật khẩu thành công"));
    }

}
