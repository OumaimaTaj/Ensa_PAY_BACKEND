package com.example.demo.dto;

import com.example.demo.model.Client;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class ClientDTO {

    private Long id;

    @JsonUnwrapped
    @JsonIgnoreProperties({"id"})
    private UserDto user;

    @JsonIgnoreProperties({"address", "agency"})
    private AgentDto agent;


    public static Client toEntity(ClientDTO clientDto) {
        if (clientDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            ClientDTO.class.getName()
                    )
            );
        }

        Client client = Client.builder()
                .user(UserDto.toEntity(clientDto.user))
                .agent(AgentDto.toEntity(clientDto.agent))
                .build();
        client.setId(client.getId());

        return client;
    }

    public static ClientDTO fromClient(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(client,clientDTO);

        return clientDTO;


//        return ClientDTO.builder()
//                .id(client.getId())
//                .user(UserDto.fromEntity(client.getUser()))
//                .agent(AgentDto.fromEntity(client.getAgent()))
//                .agency(AgencyDto.fromEntity(client.getAgency()))
//                .build();
    }

}