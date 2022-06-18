package com.example.demo.dto;

import com.example.demo.model.Agent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgentDto  {

    private Long id;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private UserDto user;


    public static Agent toEntity(AgentDto agentDto) {
        if (agentDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AgentDto.class.getName()
                    )
            );
        }

        Agent agent = Agent.builder()
                .user(UserDto.toEntity(agentDto.getUser()))
                .build();

        agent.setId(agentDto.id);

        return agent;
    }

    public static AgentDto fromEntity(Agent agent) {
        if (agent == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Agent.class.getName()
                    )
            );
        }

        return AgentDto.builder()
                .id(agent.getId())
                .user(UserDto.fromEntity(agent.getUser()))
                .build();
    }
}
