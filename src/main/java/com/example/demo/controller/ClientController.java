package com.example.demo.controller;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Client;
import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;
    private final AccountService service;

    @Autowired
    public ClientController(ClientService clientService,AccountService service) {
        this.clientService = clientService;
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<User> getClients(){
        return clientService.getClients(RoleOfUser.ROLE_CLIENT);
    }

    @PostMapping("/create/{userId}")
    public Client createClient(@RequestBody UserDto userDto,@PathVariable(name = "userId") Long userId) {
        return clientService.createClient(userDto,userId);
    }


    @GetMapping("/{id}")
    public Optional<User> getClient(@PathVariable(name = "id") Long clientid){
        return service.geUser(clientid);
    }

    @PutMapping("{id}")
    public void updateClient(@PathVariable("id") Client client, @RequestBody UserDto userDto) {
        clientService.updateClient(client, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable("id") Client client) {
        clientService.deleteClient(client);
    }

    @RequestMapping(path = "{id}/password", method = { RequestMethod.POST, RequestMethod.PUT })
    public void changePassword(@PathVariable("id") Client client, @RequestBody String passwordDto) {
        clientService.changePassword(client, passwordDto);
    }



    @GetMapping("{id}/accounts")
    public List<AccountDto> getClientAccountsList(@PathVariable("id") Client client) {

        return clientService.getClientAccountsList(client);
    }

}
