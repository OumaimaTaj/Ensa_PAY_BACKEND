package com.example.demo.mappers;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.AccountOperationDto;
import com.example.demo.dto.AgentDto;
import com.example.demo.dto.ClientDTO;
import com.example.demo.model.Account;
import com.example.demo.model.AccountOperation;
import com.example.demo.model.Agent;
import com.example.demo.model.Client;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountMapperImpl {
    public AgentDto fromAgent(Agent agent){

        AgentDto agentDto = new AgentDto();
        BeanUtils.copyProperties(agent,agentDto);
        System.out.println(agentDto);
        return agentDto;

    }

    public Agent fromAgentDTO(AgentDto agentDto){
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDto,agent);
        return agent;
    }

    public ClientDTO fromClient (Client client){

        ClientDTO clientDto = new ClientDTO();
        BeanUtils.copyProperties(client,clientDto);
        return clientDto;

    }

    public Client fromClientDTO(ClientDTO clientDto){
        Client client = new Client();
        BeanUtils.copyProperties(clientDto,client);
        return client;
    }

    public AccountDto fromAccount(Account account){

      AccountDto accountDto= new AccountDto();
      BeanUtils.copyProperties(account,accountDto);
       accountDto.setClientDto(fromClient(account.getClient()));

       return accountDto;
    }

    public Account fromAccount(AccountDto accountDto){


        Account account= new Account();
        BeanUtils.copyProperties(accountDto,account);
        account.setClient(fromClientDTO(accountDto.getClientDto()));

        return account;
    }

    public AccountOperationDto fromAccountOperation(AccountOperation accountOp){

        AccountOperationDto accountOperationDto= new AccountOperationDto();
        BeanUtils.copyProperties(accountOp,accountOperationDto);
       return accountOperationDto;

    }

    public AccountOperation fromAccountOperation(AccountOperationDto accountOpDto){



        AccountOperation accountOperation= new AccountOperation();
        BeanUtils.copyProperties(accountOpDto,accountOperation);


        return  accountOperation;
    }



}
