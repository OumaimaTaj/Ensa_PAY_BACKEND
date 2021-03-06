package com.example.demo.service;


import com.example.demo.dto.AgentDto;
import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.enums.RoleOfUser;
import com.example.demo.model.Agent;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AgentService {
//    List<ClientDTO> getAgentClientsList(Agent agent);

    void updateAgent(Long userId, UserDto userDto);

    void changePassword(User agent, UserDto passwordDto);

    Agent createAgent(UserDto userDto);

    List<AgentDto> getAllAgentsList();

    void deleteAgent(User agent);

    AgentDto getAgent(Agent agent);

    List<User> getAgents(RoleOfUser role);
    Optional<Agent> findByUser(Long agent);

}