package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {


    // tìm theo khoa
    List<NhanVien> findByKhoa_Id(Integer maKhoa);

    // tìm theo khoa + phòng ban
    List<NhanVien> findByKhoa_IdAndPhongBan_Id(Integer maKhoa, Integer maPhongBan);
}

