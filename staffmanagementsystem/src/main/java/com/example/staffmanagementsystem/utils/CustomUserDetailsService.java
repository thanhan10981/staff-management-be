package com.example.staffmanagementsystem.utils;

import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.entity.PhanQuyen;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.repository.NguoiDungQuyenRepository;
import com.example.staffmanagementsystem.repository.PhanQuyenRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepo;
    private final NguoiDungQuyenRepository nguoiDungQuyenRepo;
    private final PhanQuyenRepository phanQuyenRepo;

    public CustomUserDetailsService(NguoiDungRepository nguoiDungRepo,
                                    NguoiDungQuyenRepository nguoiDungQuyenRepo,
                                    PhanQuyenRepository phanQuyenRepo) {
        this.nguoiDungRepo = nguoiDungRepo;
        this.nguoiDungQuyenRepo = nguoiDungQuyenRepo;
        this.phanQuyenRepo = phanQuyenRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDung user = nguoiDungRepo.findByTenDangNhap(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = new HashSet<>();

        // 🔥 KHÔNG dùng VaiTro làm ROLE nữa!
        // if(user.getVaiTro() != null) {
        //     authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getVaiTro()));
        // }

        // 🔥 Lấy quyền thực sự từ bảng PhanQuyen
        List<Integer> quyenIds = nguoiDungQuyenRepo.findQuyenIdsByNguoiDung(user.getMaNguoiDung());
        if (quyenIds != null && !quyenIds.isEmpty()) {
            List<PhanQuyen> quyenList = phanQuyenRepo.findAllById(quyenIds);
            quyenList.forEach(q -> authorities.add(new SimpleGrantedAuthority(q.getTenQuyen())));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getTenDangNhap(),
                user.getMatKhauHash(),
                true, true, true, true,
                authorities
        );
    }

}
