package com.example.demo.dto;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserDto {


    private Long id;

    @Size(max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;
    @Size(max = 120)
    private String password;
    private String first_time="not_change";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;


    private String IDCard;

    private String phoneNumber;

    private String address;

    private String image;
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Role> roles;

    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            UserDto.class.getName()
                    )
            );
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .birthday(userDto.getBirthday())
                .IDCard(userDto.getIDCard())
                .first_time(userDto.getFirst_time())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .roles(userDto.getRoles())
                .build();
        user.setId(userDto.id);
        return user;
    }

    public static UserDto fromEntity(User user) {
        if (user == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            User.class.getName()
                    )
            );
        }

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .IDCard(user.getIDCard())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .first_time(user.getFirst_time())
                .roles(user.getRoles())
                .build();
    }
}