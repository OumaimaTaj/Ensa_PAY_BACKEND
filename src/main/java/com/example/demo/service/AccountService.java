package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.AccountNotFoundException;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {




    AccountDto saveAccount(double initialBalence, long clientId);



    Optional<User> geUser(Long clientId);

    AccountDto getAccount(String accountID)throws AccountNotFoundException;

     void debit(String accountId, double amount) ;



    void credit(String accountId, double amount);

    void detlete(String accounId);

    void transfer(String accountIdSource,String accountIdDestination, double amount);


    List<AccountDto> AccountList();


    List<AccountOperationDto> accountHistory(String accountId);

    AgentDto getAgent(Long customerId) ;
    AccountHistoryDto getAccountHistory(String accountId, int page, int size);
}

