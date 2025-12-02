package com.example.staffmanagementsystem.service.schedule;

import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.dto.schedule.NhanVienScheduleDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ShiftService {

    // Lấy lịch trực của một khoa theo khoảng thời gian
    List<LichTrucNgayDTO> getLichTrucByKhoa(Integer maKhoa, LocalDate from, LocalDate to);

    // Gán 1 ca trực đơn lẻ
    LichTrucNgayDTO assignSingleShift(LichTrucNgayDTO dto) throws IllegalArgumentException;

    // Tạo phân công và tự động generate lịch trực
    List<LichTrucNgayDTO> createPhanCongAndGenerateLich(PhanCongCaTrucDTO dto) throws IllegalArgumentException;

    // Lấy chi tiết một ca trực
    LichTrucNgayDTO getShiftById(Integer id) throws IllegalArgumentException;

    // Cập nhật ca trực
    LichTrucNgayDTO updateShift(Integer id, LichTrucNgayDTO dto) throws IllegalArgumentException;

    // Xóa một ca trực
    void deleteShift(Integer id) throws IllegalArgumentException;

    // Xóa tất cả shift được tạo từ một phân công ca trực
    int deleteShiftsByPhanCong(Integer maPhanCong) throws IllegalArgumentException;

    // Cập nhật trạng thái ca trực (PENDING, APPROVED, DONE, CANCELED…)
    LichTrucNgayDTO updateShiftStatus(Integer id, String status) throws IllegalArgumentException;
    Map<String, Long> getShiftStats(Integer maKhoa, LocalDate from, LocalDate to);

    List<NhanVienScheduleDTO> getNhanVienTheoKhoaPhong(Integer maKhoa, Integer maPhongBan);
}
