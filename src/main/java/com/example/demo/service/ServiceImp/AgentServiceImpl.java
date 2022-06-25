package com.example.demo.service.ServiceImp;

import com.example.demo.dto.AgentDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.exceptions.InvalidCredentialsException;
import com.example.demo.model.Admin;
import com.example.demo.model.Agent;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.AgentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AgentService;
import com.example.demo.service.ImageService;
import com.example.demo.service.MailService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private  final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final ImageService imageService;
    // Logger
    private final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository,
                            PasswordEncoder passwordEncoder,
                            UserRepository userRepository,
                            AdminRepository adminRepository,
                            RoleRepository roleRepository,
                            MailService mailService,
                            ImageService imageService) {
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.mailService = mailService;
        this.imageService = imageService;
    }

    @Override
    public Agent createAgent(UserDto userDto) {
        this.logger.info("Create new agent {}",userDto.getUsername());

        if(userRepository.existsByEmail(userDto.getEmail())){
            throw new InvalidCredentialsException("Email est invalide");
        }
      //  if(userRepository.existsByIDCard(userDto.getIDCard())){
      //      throw new InvalidCredentialsException("Numéro pièce d'identité est invalide");
       // }

        // generate password
        String password = this.passwordGenerator();
        // Create user
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .birthday(userDto.getBirthday())
                .IDCard(userDto.getIDCard())
                .password(passwordEncoder.encode(password))
                .image(userDto.getImage())
                .description(userDto.getDescription())
                .build();
        user.setFirst_time("not_change");
        Set<Role> roles = new HashSet<>();
        Role modRole = roleRepository.findByName(RoleOfUser.ROLE_AGENT);
        roles.add(modRole);
        user.setRoles(roles);
        userRepository.save(user);
        // We have one Admin with id = 1
        Admin admin= adminRepository.findById(1L).get();
        Agent newAgent  = Agent.builder()
                .user(user).admin(admin).build();
        agentRepository.save(newAgent);
        this.mailService.sendPasswordMail(user,password);
        return newAgent;
    }

    @Override
    public void updateAgent(Agent agent, UserDto userDto) {
        User user = agent.getUser();

        // Check if the current user is an admin
        for(Role role:user.getRoles()) {
            if (role.getName().equals(RoleOfUser.ROLE_AGENT)) {
                // Check if the idcard changed
              //  if (!user.getIDCard().equals(userDto.getIDCard())) {
                    // Check if the idcard is unique
                 //   if (userRepository.existsByIDCard(userDto.getIDCard())) {
                     //   throw new RuntimeException("IDCard is already taken");
                 //   }
               // }
                user.setIDCard(userDto.getIDCard());
                user.setFirst_time("changed");

                user.setUsername(userDto.getUsername());
            }
        }
        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("Email is already taken");
            }
        }
        user.setEmail(userDto.getEmail());

        if ( ! Strings.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public void changePassword(Agent agent, String passwordDto) {
        User user = agent.getUser();
        for(Role role:user.getRoles()) {
        if (! role.getName().equals(RoleOfUser.ROLE_ADMIN)) {
            if (!Objects.equals(passwordDto, user.getPassword())) {
                throw new RuntimeException("The current password is incorrect.");
            }
        }

        user.setPassword(
                passwordDto
        );}

        userRepository.save(user);
    }


    @Override
    public List<AgentDto> getAllAgentsList() {
        return agentRepository.findAll()
                .stream()
                .map(AgentDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAgent(User agent) {
        userRepository.delete(agent);
    }

    @Override
    public AgentDto getAgent(Agent agent) {
        return AgentDto.fromEntity(agent);
    }

    @Override
    public List<User> getAgents(RoleOfUser role) {
        return agentRepository.findBySpecificRoles(role);
    }
    // Password generator
    private String passwordGenerator(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
