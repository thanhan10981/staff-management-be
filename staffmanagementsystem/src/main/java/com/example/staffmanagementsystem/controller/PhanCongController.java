package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.dto.schedule.PhanCongTheoDotDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.service.LichTrucService;
import com.example.staffmanagementsystem.service.PhanCongCaTrucService;
import com.example.staffmanagementsystem.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/phancong")
@RequiredArgsConstructor
public class PhanCongController {

    private final LichTrucService lichTrucService;
    private final PhanCongCaTrucService phanCongCaTrucService;

    // ==============================
    // 1) Tạo phân công tuần + sinh lịch trực ngày
    // ==============================
    @PostMapping("/tuan")
    public ResponseEntity<?> taoLichTheoTuan(
            @RequestParam Integer maNV,
            @RequestParam Integer maPhong,
            @RequestParam Integer maKhoa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start
    ) {
        Integer actorId = SecurityUtils.getCurrentUserId();

        lichTrucService.taoPhanCongTuan(maNV, maPhong, maKhoa, start, actorId);
        return ResponseEntity.ok("Tạo phân công + lịch trực thành công!");
    }

    // ==============================
    // 2) Lấy danh sách phân công theo nhân viên
    // ==============================
    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<List<PhanCongCaTrucDTO>> getPhanCongTheoNhanVien(@PathVariable Integer maNV) {
        return ResponseEntity.ok(phanCongCaTrucService.getByNhanVien(maNV));
    }

    // ==============================
    // 3) Lấy phân công theo ID
    // ==============================
    @GetMapping("/{id}")
    public ResponseEntity<PhanCongCaTrucDTO> getPhanCongTheoId(@PathVariable Integer id) {
        return ResponseEntity.ok(phanCongCaTrucService.getById(id));
    }

    // ==============================
    // 4) Lấy tất cả phân công
    // ==============================
    @GetMapping
    public ResponseEntity<List<PhanCongCaTrucDTO>> getAll() {
        return ResponseEntity.ok(phanCongCaTrucService.getAll());
    }

    // ==============================
    // 5) Lấy lịch trực ngày theo tuần
    // ==============================
    @GetMapping("/lich-tuan")
    public ResponseEntity<List<LichTrucNgayDTO>> getLichTheoTuan(
            @RequestParam Integer maNV,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        return ResponseEntity.ok(lichTrucService.getLichTheoTuan(maNV, start, end));
    }

    @PostMapping("/create-with-lich")
    public ResponseEntity<?> taoPhanCongVaLich(@RequestBody PhanCongTheoDotDTO dto) {
        phanCongCaTrucService.taoPhanCongVaSinhLich(dto);
        return ResponseEntity.ok("Created successfully");
    }

}
