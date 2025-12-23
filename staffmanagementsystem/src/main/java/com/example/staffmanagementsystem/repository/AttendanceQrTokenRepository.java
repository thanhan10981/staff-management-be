package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.AttendanceQrToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceQrTokenRepository extends JpaRepository<AttendanceQrToken, Long> {
    Optional<AttendanceQrToken> findByToken(String token); // map token = MaQRCode
}