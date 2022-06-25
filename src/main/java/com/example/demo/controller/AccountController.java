package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exceptions.AccountNotFoundException;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.model.Account;
import com.example.demo.model.Client;
import com.example.demo.service.AccountService;
import com.example.demo.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class AccountController {

    private  AccountService accountservice;

    private ClientService clientService;

    public AccountController(AccountService accountservice, ClientService clientService) {
        this.accountservice = accountservice;
        this.clientService = clientService;


    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/accounts/{accountId}")

    public AccountDto getAccount( @PathVariable String accountId) throws AccountNotFoundException {


        return accountservice.getAccount(accountId);

    }



    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @GetMapping("/accounts")

    public List<AccountDto> account (){

        return
                accountservice.AccountList();
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDto> getHistory(@PathVariable String accountId){
        return accountservice.accountHistory(accountId);
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @PostMapping("account/{clientId}")
    public AccountDto saveAccount(@PathVariable long clientId, @RequestBody double balence){
        return accountservice.saveAccount(balence,clientId);
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDto getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws AccountNotFoundException {
        return accountservice.getAccountHistory(accountId,page,size);
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        this.accountservice.debit(debitDTO.getAccountId(),debitDTO.getAmount());
        return debitDTO;
    }


    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException {
        this.accountservice.credit(creditDTO.getAccountId(),creditDTO.getAmount());
        return creditDTO;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")

    @PostMapping("/accounts/transfer")
    public TransferRequestDTO transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        this.accountservice.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());

        return transferRequestDTO;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("{id}/accounts")
    public Account getClientAccountsList(@PathVariable(name = "id") Client client) {

        return clientService.getClientAccountsList(client);
    }


}
