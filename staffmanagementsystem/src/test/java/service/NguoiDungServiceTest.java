package service;

import com.example.staffmanagementsystem.dto.UserResponseDTO;
import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.repository.NguoiDungQuyenRepository;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.service.impl.NguoiDungServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;



@ExtendWith(MockitoExtension.class)
public class NguoiDungServiceTest {
    @InjectMocks
    private NguoiDungServiceImpl service;

    @Mock
    private NguoiDungRepository nguoiDungRepository;

    @Mock
    private NguoiDungQuyenRepository nguoiDungQuyenRepository;

    @Test
    void getAllUsers_shouldReturnList() {
        NguoiDung user = new NguoiDung();
        user.setMaNguoiDung(1);
        user.setTenDangNhap("admin");

        when(nguoiDungRepository.findAll()).thenReturn(List.of(user));
        when(nguoiDungQuyenRepository.findQuyenIdsByNguoiDung(1))
                .thenReturn(List.of(1,2));

        List<UserResponseDTO> result = service.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("admin", result.get(0).getTenDangNhap());
    }

    @Test
    void deleteOne_shouldDeleteUserAndPermission() {
        service.deleteOne(1);

        verify(nguoiDungQuyenRepository).deleteByNguoiDungId(1);
        verify(nguoiDungRepository).deleteById(1);
    }

}
