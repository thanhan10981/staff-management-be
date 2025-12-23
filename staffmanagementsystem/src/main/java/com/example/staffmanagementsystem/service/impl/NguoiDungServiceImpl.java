package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.CreateUserRequest;
import com.example.staffmanagementsystem.dto.UpdateUserRequest;
import com.example.staffmanagementsystem.dto.UserResponseDTO;
import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.NguoiDungQuyenRepository;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.service.NguoiDungService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {
    private final NguoiDungRepository nguoiDungRepository;
    private final NguoiDungQuyenRepository nguoiDungQuyenRepository;

    public NguoiDungServiceImpl(NguoiDungRepository nguoiDungRepository, NguoiDungQuyenRepository nguoiDungQuyenRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.nguoiDungQuyenRepository = nguoiDungQuyenRepository;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<NguoiDung> users = nguoiDungRepository.findAll();
        List<UserResponseDTO> result = new ArrayList<>();
        for (NguoiDung u : users) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setMaNguoiDung(u.getMaNguoiDung());
            dto.setTenDangNhap(u.getTenDangNhap());
            dto.setVaiTro(u.getVaiTro());
            dto.setTrangThai(u.getTrangThai());
            if (u.getNhanVien() != null) {
                NhanVien nv = u.getNhanVien();
                dto.setMaNhanVien(nv.getMaNhanVien());
                dto.setTenNhanVien(nv.getTenNhanVien());
            }
            dto.setPermissionIds(nguoiDungQuyenRepository.findQuyenIdsByNguoiDung(u.getMaNguoiDung()));
            result.add(dto);
        }
        return result;
    }

    @Override
    @Transactional
    public void deleteOne(Integer id) {
        nguoiDungQuyenRepository.deleteByNguoiDungId(id);
        nguoiDungRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMany(List<Integer> ids) {
        nguoiDungQuyenRepository.deleteByNguoiDungIds(ids);
        nguoiDungRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest request) {
        NguoiDung user = new NguoiDung();
        user.setTenDangNhap(request.getTenDangNhap());
        user.setMatKhauHash(request.getMatKhau());
        user.setVaiTro(request.getVaiTro());
        user.setTrangThai("HoatDong");
        user.setMaNhanVien(request.getMaNhanVien());
        NguoiDung savedUser = nguoiDungRepository.save(user);
        if (request.getPermissionIds() != null) {
            for (Integer maQuyen : request.getPermissionIds()) {
                nguoiDungQuyenRepository.insertNguoiDungQuyen(savedUser.getMaNguoiDung(), maQuyen);
            }
        }
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UpdateUserRequest request) {
        NguoiDung user = nguoiDungRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setTenDangNhap(request.getTenDangNhap());
        user.setVaiTro(request.getVaiTro());
        user.setMaNhanVien(request.getMaNhanVien());
        nguoiDungRepository.save(user);
        nguoiDungQuyenRepository.deleteByNguoiDungId(id);
        if (request.getPermissionIds() != null) {
            for (Integer maQuyen : request.getPermissionIds()) {
                nguoiDungQuyenRepository.insertNguoiDungQuyen(id, maQuyen);
            }
        }
    }
}