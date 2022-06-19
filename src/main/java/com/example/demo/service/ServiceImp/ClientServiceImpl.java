package com.example.demo.service.ServiceImp;


import com.example.demo.dto.AccountDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.mappers.AccountMapperImpl;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.ClientService;
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
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private  final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private AccountMapperImpl mapper;
    private final AgentRepository agentRepository;
    private final MailService mailService;
    // Logger
    private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);


    @Autowired
    public ClientServiceImpl(UserRepository userRepository,
                             ClientRepository clientRepository,
                             AccountRepository accountRepository,
                             PasswordEncoder passwordEncoder,
                             RoleRepository roleRepository,
                             AgentRepository agentRepository,
                             MailService mailService) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.agentRepository = agentRepository;
        this.mailService = mailService;
    }

    @Override
    public void updateClient(Client client, UserDto userDto) {
        User user = client.getUser();

        // Check if the email is already taken
        if ( ! user.getEmail().equals(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("Email is already taken");
            }
        }
       user.setUsername(user.getUsername());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setIDCard(userDto.getIDCard());
        user.setBirthday(userDto.getBirthday());
        user.setFirst_time("changed");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        if ( ! Strings.isNullOrEmpty(userDto.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteClient(Client client) {
        // Delete the client
        // All the data related to this client will be delete as well thanks to cascade option.
        clientRepository.delete(client);
    }

    @Override
    public Client createClient(UserDto userDto, Long userId) {
        this.logger.info("Create new client {}",userDto.getUsername());

        // generate password
        String password = this.passwordGenerator();
        // generate client e_code
        Integer e_code = this.generateECode();
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
                .birthday(userDto.getBirthday())
                .IDCard(userDto.getIDCard())
                .password(passwordEncoder.encode(password))
                .e_code(e_code)
                .build();
        user.setFirst_time("not_change");
        Set<Role> roles = new HashSet<>();
        Role modRole = roleRepository.findByName(RoleOfUser.ROLE_CLIENT);
        roles.add(modRole);
        user.setRoles(roles);;
        userRepository.save(user);
        Client newClient  = Client.builder()
                .user(user)
                .build();
        // find agent by user id
        Agent agent = agentRepository.findByUser(userRepository.findById(userId).get());
        System.out.println(agent.getId());
        newClient.setAgent(agent);
        clientRepository.save(newClient);
        // Send generated password and e-code to client
        mailService.sendPasswordAndECodeMail(user,password,user.getE_code());
        return newClient;
    }

    @Override
    public void changePassword(Client client, String passwordDto) {
        User user = client.getUser();
     for(Role role:user.getRoles()) {
         if (!role.getName().equals(RoleOfUser.ROLE_ADMIN) &&
                 !role.getName().equals(RoleOfUser.ROLE_AGENT)) {
             if (!Objects.equals(passwordDto, user.getPassword())) {
                 throw new RuntimeException("The current password is incorrect.");
             }
         }

         user.setPassword(
                 passwordDto
         );
     }
        userRepository.save(user);
    }

    @Override
    public List<AccountDto> getClientAccountsList(Client client) {
        return accountRepository.findAllByClient(client)
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getClients(RoleOfUser role) {

        return clientRepository.findBySpecificRoles(role);
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
    private Integer generateECode(){
        return new Random().nextInt(9000) + 1000;
    }

}