package com.example.demo.dto;

import com.example.demo.model.Admin;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AdminDto {
    private Long id;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private UserDto user;

    public static Admin toEntity(AdminDto adminDto) {
        if (adminDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AdminDto.class.getName()
                    )
            );
        }

        Admin admin = Admin.builder()
                .user(UserDto.toEntity(adminDto.user))
                .build();
        admin.setId(admin.getId());

        return admin;
    }

    public static AdminDto fromEntity(Admin admin) {
        if (admin == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Admin.class.getName()
                    )
            );
        }

        return AdminDto.builder()
                .id(admin.getId())
                .user(UserDto.fromEntity(admin.getUser()))
                .build();
    }
}