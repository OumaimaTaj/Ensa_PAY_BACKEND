package com.example.demo.controller;

import com.example.demo.dto.AdminDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AdminDto getAdmin(@PathVariable("id") Admin admin) {
        return adminService.getAdmin(admin);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public void updateAdmin(@PathVariable("id") Admin admin,
                            @RequestBody UserDto userDto) {
        adminService.updateAdmin(admin, userDto);
    }
}
