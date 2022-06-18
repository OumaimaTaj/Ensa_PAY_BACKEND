package com.example.demo.service;

import com.example.demo.dto.AdminDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    void updateAdmin(Admin admin, UserDto userDto);

    AdminDto getAdmin(Admin admin);
}
