package com.example.staffmanagementsystem.controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.EnumResponse;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/loai-thong-ke-ngay-cong")
    public List<EnumResponse> getLoaiThongKeNgayCong() {
        return Arrays.stream(LoaiThongKeNgayCong.values())
                .map(e -> new EnumResponse(
                        e.name(),
                        mapTenTiengViet(e)
                ))
                .toList();
    }

    private String mapTenTiengViet(LoaiThongKeNgayCong e) {
        return switch (e) {
            case THANG_NAY -> "Tháng này";
            case THANG_TRUOC -> "Tháng trước";
            case QUY_NAY -> "Quý này";
            case QUY_TRUOC -> "Quý trước";
            case NAM_NAY -> "Năm nay";
            case NAM_TRUOC -> "Năm trước";
        };
    }
}
