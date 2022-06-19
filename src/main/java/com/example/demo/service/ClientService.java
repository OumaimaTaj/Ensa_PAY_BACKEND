package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Client;
import com.example.demo.model.User;

import java.util.List;

public interface ClientService {
    void updateClient(Client client, UserDto userDto);

    void deleteClient(Client client);

    Client createClient(UserDto userDto, Long userId);

    void changePassword(Client client, String password);

//    List<AccountDto> getClientAccountsList(Client client);

    List<AccountDto> getClientAccountsList(Client client);
    List<User> getClients(RoleOfUser role);


}