package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.model.Facture;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    void updateClient(Client client, UserDto userDto);

    void deleteClient(Client client);

    Client createClient(UserDto userDto, Long userId);

    void changePassword(User client, UserDto userDto);

//    List<AccountDto> getClientAccountsList(Client client);

    Account getClientAccountsList(Client client);
    List<User> getClients(RoleOfUser role);
     void updateClient(User client, UserDto userDto);





//    List<AccountDto> getClientAccountsList(Client client);
    Optional<Client> findByUser(Long client);
    List<Facture> findFacture(Long id);
    void updateFacture(Long id);
    void createFacture(FactureDto factureDto, Long id);


    List<Facture> findFactureByAgence(Long id, String agence);


}
