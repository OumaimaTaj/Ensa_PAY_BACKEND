package com.example.demo.controller;

import com.example.demo.dto.FactureDto;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Client;
import com.example.demo.model.Facture;
import com.example.demo.model.User;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final FactureRepository factureRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientController(ClientService clientService, AccountService service, FactureRepository factureRepository, PasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.service = service;
        this.factureRepository = factureRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping("/getAll")
    public List<User> getClients(){
        return clientService.getClients(RoleOfUser.ROLE_CLIENT);
    }
    @PreAuthorize("hasRole('ROLE_AGENT')")
    @PostMapping("/create/{userId}")
    public Client createClient(@RequestBody UserDto userDto,@PathVariable(name = "userId") Long userId) {
        return clientService.createClient(userDto,userId);
    }

    @PreAuthorize("hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT')")
    @GetMapping("/{id}")
    public Optional<User> getClient(@PathVariable(name = "id") Long clientid){
        return service.geUser(clientid);
    }
    @PreAuthorize("hasRole('ROLE_AGENT')")
    @PutMapping("{id}")
    public void updateClient(@PathVariable("id") Client client, @RequestBody UserDto userDto) {
        clientService.updateClient(client, userDto);
    }
    @PreAuthorize("hasRole('ROLE_AGENT')")
    @DeleteMapping("{id}")
    public void deleteClient(@PathVariable("id") User client) {
        clientService.deleteClient(client);
    }
    @PreAuthorize("hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT')")
    @RequestMapping(path = "{id}/password", method = { RequestMethod.POST, RequestMethod.PUT })
    public void changePassword(@PathVariable("id") User client, @RequestBody UserDto passwordDto) {
        clientService.changePassword(client, passwordDto);
    }
    @PreAuthorize("hasRole('ROLE_AGENT') or hasRole('ROLE_CLIENT')")

    @GetMapping("find/{id}")
    public Optional<Client> getClientById(@PathVariable("id") Long client) {

        return  clientService.findByUser(client);
    }
    @PreAuthorize(" hasRole('ROLE_CLIENT')")
    @GetMapping("facture/{id}")
    public List<Facture> getAllFacture(@PathVariable("id") Long client){
        return clientService.findFacture(client);
    }
    @PreAuthorize(" hasRole('ROLE_CLIENT')")
    @PutMapping("facture/update/{id}")
    public void updateFacture(@RequestBody FactureDto factureDto ,@PathVariable("id") Long id ){
        clientService.updateFacture(id);
    }
    @PreAuthorize(" hasRole('ROLE_CLIENT')")
    @PostMapping("facture/add/{id}")
    public void addFacture(@RequestBody FactureDto factureDto, @PathVariable("id") Long id ){
        clientService.createFacture(factureDto,id);
    }
    @PreAuthorize(" hasRole('ROLE_CLIENT')")
    @GetMapping("facture/{id}/{agence}")
    public List<Facture> getAllFacture(@PathVariable("id") Long idClient, @PathVariable("agence") String agence){
        return clientService.findFactureByAgence(idClient,agence);
    }
    @GetMapping("test/{id}")

    public boolean test_password(@RequestBody String hach,@PathVariable("id") User user){
        System.out.println(user.getPassword());
        return  new BCryptPasswordEncoder().matches(hach,user.getPassword());

    }
}
