package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.UserResponseDTO;

import java.util.List;

import com.example.staffmanagementsystem.dto.CreateUserRequest;
import com.example.staffmanagementsystem.dto.UpdateUserRequest;

public interface NguoiDungService {
    List<UserResponseDTO> getAllUsers();

    void deleteOne(Integer id);

    void deleteMany(List<Integer> ids);

    void createUser(CreateUserRequest request);

    void updateUser(Integer id, UpdateUserRequest request);
}