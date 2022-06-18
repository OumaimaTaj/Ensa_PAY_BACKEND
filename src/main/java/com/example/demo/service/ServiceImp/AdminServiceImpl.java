package com.example.demo.service.ServiceImp;


import com.example.demo.dto.AdminDto;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Admin;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateAdmin(Admin admin, UserDto userDto) {
        User user = admin.getUser();

        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException( "Email is already taken");
            }
        }
        user.setEmail(userDto.getEmail());
        if ( ! Strings.isNullOrEmpty(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
        }


        if ( ! Strings.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        if ( ! Strings.isNullOrEmpty(userDto.getIDCard())) {
            user.setIDCard(userDto.getIDCard());
        }


        userRepository.save(user);
    }

    @Override
    public AdminDto getAdmin(Admin admin) {
        return AdminDto.fromEntity(admin);
    }
}
