package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.dto.LichTrucTuanDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LichTrucService {

    // Lấy lịch theo ngày
    List<LichTrucNgayDTO> getLichTheoNgay(LocalDate date);

    // Lấy lịch theo tháng cho 1 khoa
    List<LichTrucNgayDTO> getLichTheoThang(Integer maKhoa, int year, int month);

    // Lấy lịch theo phòng trong khoảng
    List<LichTrucNgayDTO> getLichTheoPhong(Integer maPhong, LocalDate from, LocalDate to);

    // Lấy lịch theo nhân viên
    List<LichTrucNgayDTO> getLichTheoNhanVien(Integer maNV);

    // Kiểm tra thiếu nhân sự cho phòng/ca/ngày (trả required/current/breakdown)
    Map<String, Object> checkThieuNhanSu(Integer maPhong, Integer maCa, LocalDate date);

    // Tạo phân công tuần + sinh lịch 7 ngày (được controller gọi)
    void taoPhanCongTuan(Integer maNV, Integer maPhong, Integer maKhoa, LocalDate start, Integer actorId);

    // Lấy lịch theo tuần cho 1 nhân viên
    List<LichTrucNgayDTO> getLichTheoTuan(Integer maNV, LocalDate start, LocalDate end);

    // Các CRUD cơ bản có thể thêm (get by id / update / delete)
    LichTrucNgayDTO getShiftById(Integer id);
    LichTrucNgayDTO updateShift(Integer id, LichTrucNgayDTO dto);
    void deleteShift(Integer id);

    Map<Integer, Long> getEmployeeCountByNgay(
            Integer maKhoa,
            LocalDate date
    );

    long tinhCaThieuNguoi(List<LichTrucNgayDTO> shifts);
    long tinhCaXungDot(List<LichTrucNgayDTO> shifts);
    // lấy lịch tuần theo khoa
    List<LichTrucTuanDTO> getBangLichTuanTheoKhoa(
            Integer maKhoa,
            LocalDate start,
            LocalDate end
    );
    // lấy lịch tuần theo phòng
    List<LichTrucTuanDTO> getBangLichTuanTheoPhong(
            Integer maPhong,
            LocalDate start,
            LocalDate end
    );

}