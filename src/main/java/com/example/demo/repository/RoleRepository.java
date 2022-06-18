package com.example.demo.repository;

import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(RoleOfUser name);
}
