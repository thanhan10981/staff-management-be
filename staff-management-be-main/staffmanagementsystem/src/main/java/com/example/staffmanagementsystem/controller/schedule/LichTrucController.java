    package com.example.staffmanagementsystem.controller.schedule;
    import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
    import com.example.staffmanagementsystem.service.schedule.ShiftService;
    import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
    import com.example.staffmanagementsystem.service.LichTrucService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.format.annotation.DateTimeFormat;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    
    import java.time.LocalDate;
    import java.util.List;
    import java.util.Map;
    import java.util.Objects;
    import com.example.staffmanagementsystem.dto.LichTrucTuanDTO;
    import org.springframework.format.annotation.DateTimeFormat;
    
    @RestController
    @RequestMapping("/api/lichtruc")
    @RequiredArgsConstructor
    public class LichTrucController {
    
        private final ShiftService shiftService;
        private final LichTrucService lichTrucService;
    
        @GetMapping("/khoa")
        public List<LichTrucNgayDTO> getByKhoa(
                @RequestParam Integer maKhoa,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
            return shiftService.getLichTrucByKhoa(maKhoa, from, to);
        }
    
        @PostMapping("/single")
        public LichTrucNgayDTO assign(@RequestBody LichTrucNgayDTO dto) {
            return shiftService.assignSingleShift(dto);
        }
    
        @PostMapping("/phancong")
        public List<LichTrucNgayDTO> createMultiple(@RequestBody PhanCongCaTrucDTO dto) {
            return shiftService.createPhanCongAndGenerateLich(dto);
        }
    
        @GetMapping("/{id}")
        public LichTrucNgayDTO detail(@PathVariable Integer id) {
            return shiftService.getShiftById(id);
        }
    
        @PutMapping("/{id}")
        public LichTrucNgayDTO update(@PathVariable Integer id, @RequestBody LichTrucNgayDTO dto) {
            return shiftService.updateShift(id, dto);
        }
    
        @PatchMapping("/{id}/status")
        public LichTrucNgayDTO updateStatus(@PathVariable Integer id, @RequestParam String status) {
            return shiftService.updateShiftStatus(id, status);
        }
    
        @DeleteMapping("/{id}")
        public void delete(@PathVariable Integer id) {
            shiftService.deleteShift(id);
        }
    
        @GetMapping("/count")
        public ResponseEntity<?> getCountByNgay(
                @RequestParam Integer maKhoa,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
        ) {
            Map<Integer, Long> result =
                    lichTrucService.getEmployeeCountByNgay(maKhoa, date);
    
            return ResponseEntity.ok(result);
        }
    
        @GetMapping("/thang")
        public ResponseEntity<?> getCountByMonth(
                @RequestParam Integer maKhoa,
                @RequestParam Integer year,
                @RequestParam Integer month
        ) {
            // Tính ngày đầu – cuối tháng
            LocalDate from = LocalDate.of(year, month, 1);
            LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
    
            // Gọi service lấy danh sách ca trực
            List<LichTrucNgayDTO> shifts =
                    shiftService.getLichTrucByKhoa(maKhoa, from, to);
    
            // Đếm tổng số ca trực
            int total = shifts.size();
    
            return ResponseEntity.ok(total);
        }
    
        @GetMapping("/nhanvien/thang")
        public ResponseEntity<?> getEmployeeCountByMonth(
                @RequestParam Integer maKhoa,
                @RequestParam Integer year,
                @RequestParam Integer month
        ) {
            LocalDate from = LocalDate.of(year, month, 1);
            LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
    
            List<LichTrucNgayDTO> shifts =
                    shiftService.getLichTrucByKhoa(maKhoa, from, to);
    
            // Lấy unique nhân viên theo MaNhanVien
            long totalEmployees = shifts.stream()
                    .map(LichTrucNgayDTO::getMaNhanVien)
                    .filter(Objects::nonNull)
                    .distinct()
                    .count();
    
            Map<String, Object> response = Map.of(
                    "maKhoa", maKhoa,
                    "month", month,
                    "year", year,
                    "totalEmployees", totalEmployees
            );
    
            return ResponseEntity.ok(response);
        }
    
        @GetMapping("/stats/thang")
        public ResponseEntity<?> getMonthlyShiftStats(
                @RequestParam Integer maKhoa,
                @RequestParam Integer year,
                @RequestParam Integer month
        ) {
            LocalDate from = LocalDate.of(year, month, 1);
            LocalDate to = from.withDayOfMonth(from.lengthOfMonth());
    
            List<LichTrucNgayDTO> shifts =
                    shiftService.getLichTrucByKhoa(maKhoa, from, to);
    
            long thieuNguoi = lichTrucService.tinhCaThieuNguoi(shifts);
            long xungDot = lichTrucService.tinhCaXungDot(shifts);
    
            return ResponseEntity.ok(Map.of(
                    "maKhoa", maKhoa,
                    "year", year,
                    "month", month,
                    "thieuNguoi", thieuNguoi,
                    "xungDot", xungDot
            ));
        }
    
        // ===== LỊCH TUẦN THEO KHOA =====
        @GetMapping("/tuan/khoa/{maKhoa}")
        public List<LichTrucTuanDTO> getTheoKhoa(
                @PathVariable Integer maKhoa,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
        ) {
            return lichTrucService.getBangLichTuanTheoKhoa(maKhoa, from, to);
        }
    
        // ===== LỊCH TUẦN THEO PHÒNG =====
        @GetMapping("/tuan/phong/{maPhong}")
        public List<LichTrucTuanDTO> getTheoPhong(
                @PathVariable Integer maPhong,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
        ) {
            return lichTrucService.getBangLichTuanTheoPhong(maPhong, from, to);
        }
    
    
    }
