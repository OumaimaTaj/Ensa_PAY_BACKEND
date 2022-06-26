package com.example.demo.controller;

import com.example.demo.dto.AgentDto;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Agent;
import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;
    private final AccountService accountService;

    @Autowired
    public AgentController(AgentService agentService,AccountService accountService ) {

        this.agentService = agentService;
        this.accountService = accountService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/getAll")
    public List<User> getClients(){
        return agentService.getAgents(RoleOfUser.ROLE_AGENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public void updateAgent(@PathVariable("id") User agent, @RequestBody UserDto userDto) {
        agentService.updateAgent(agent, userDto);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void deleteAgent(@PathVariable("id") User agent) {
        agentService.deleteAgent(agent);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AGENT')")
    @RequestMapping(path = "{id}/password", method = { RequestMethod.POST, RequestMethod.PUT })
    public void changePassword(@PathVariable("id") User agent, @RequestBody UserDto passwordDto) {
        agentService.changePassword(agent, passwordDto);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public User getAgent(@PathVariable(name = "id") Long agentId)  {
        return accountService.getAgent(agentId);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public Agent createAgent(@RequestBody UserDto userDto) {
        return agentService.createAgent(userDto);
    }

    @GetMapping("find/{id}")
    public Optional<Agent> getClientById(@PathVariable("id") Long agent) {

        return  agentService.findByUser(agent);
    }
}
