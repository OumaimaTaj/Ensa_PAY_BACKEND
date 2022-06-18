package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.exceptions.AccountNotFoundException;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/account")
public class AccountController {

   private  AccountService accountservice;



    public AccountController(AccountService accountservice) {
        this.accountservice = accountservice;


    }

@GetMapping("/accounts/{accountId}")
    public AccountDto getAccount( @PathVariable String accountId) throws AccountNotFoundException {

    System.out.println("ftyguhi");
        return accountservice.getAccount(accountId);

    }

    @GetMapping("/accounts")

    public List<AccountDto> account (){

        return
        accountservice.AccountList();
    }
    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDto> getHistory(@PathVariable String accountId){
        return accountservice.accountHistory(accountId);
    }

    @PostMapping("account/{clientId}")
    public AccountDto saveAccount(@PathVariable long clientId, @RequestBody double balence){
        return accountservice.saveAccount(balence,clientId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDto getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws AccountNotFoundException {
        return accountservice.getAccountHistory(accountId,page,size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        this.accountservice.debit(debitDTO.getAccountId(),debitDTO.getAmount());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException {
        this.accountservice.credit(creditDTO.getAccountId(),creditDTO.getAmount());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public TransferRequestDTO transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws AccountNotFoundException, BalanceNotSufficientException {
       this.accountservice.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());

       return transferRequestDTO;
    }
}

