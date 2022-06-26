package com.example.demo.service.ServiceImp;

import com.example.demo.dto.AccountDto;
import com.example.demo.dto.AccountHistoryDto;
import com.example.demo.dto.AccountOperationDto;
import com.example.demo.dto.AgentDto;
import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.OperationType;
import com.example.demo.exceptions.AccountNotFoundException;
import com.example.demo.exceptions.BalenceNotSufficentException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.AccountMapperImpl;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Slf4j
@Service

public class AccountImplementation implements AccountService
{


 @Autowired
    private ClientRepository  clientREpo;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AccountMapperImpl mapper;
    @Autowired
    private AccountOperationRepository accountOpRepo;
    @Autowired
    private AgentRepository agentRepository;




    @Override
    public AccountDto saveAccount(double initialBalence, long clientId)  {

        Client client = clientREpo.findById(clientId).orElse(null);
        if(client == null){
            throw new UserNotFoundException("client not found");
        }
        Account account = new Account() ;



        account.setId(UUID.randomUUID().toString());
        account.setCode((int)(Math.random()*(9000-1000+1)+1000));
        account.setCreatedAt(new Date());
        account.setBalance(initialBalence);
        account.setStatus(AccountStatus.CREATED);
        account.setClient(client);
        account.setRib((int) (Math.random()*(900000000-100000000+1)+1000));
        Account savedAccount = accountRepo.save(account);


        return mapper.fromAccount(savedAccount);
    }

    @Override
    public Optional<User> geUser(Long clientId) {


        Optional<User> client = userRepo.findById(clientId);
        if ( client == null) throw new RuntimeException("Client not found");
        return client;
    }


    @Override
    public AccountDto getAccount(String accountID) {
        System.out.println("here");
        Account account = accountRepo.findById(accountID)

                .orElseThrow(() -> new AccountNotFoundException("Account not found"));


        return mapper.fromAccount(account);
    }



    @Override
    public void debit(long accountRib, double amount) {

        Account account = accountRepo.findAccountByRib(accountRib);
        // .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(account.getBalance()< amount){
            throw new BalenceNotSufficentException(" Balence not sufficient ");
        }


        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(account);
        accountOpRepo.save(accountOperation);
        account.setBalance(account.getBalance()-amount);
        accountRepo.save(account);
    }



    @Override
    public void credit (long accountRib, double amount) {


        Account account = accountRepo.findAccountByRib(accountRib);
        //  Account account = accountRepo.findAccountByRib(accountId);
        //   .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if(account.getBalance()< amount){
            throw new BalenceNotSufficentException(" Balence not sufficient ");
        };

        AccountOperation accountOperation = new AccountOperation();

        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(account);
        accountOpRepo.save(accountOperation);
        account.setBalance(account.getBalance()+amount);
        accountRepo.save(account);

    }

    @Override
    public void detlete(String accounId) {


    }



    @Override
    public void transfer(long accountIdSource, long accountIdDestination, double amount) throws AccountNotFoundException{

        debit(accountIdSource,amount);
        credit(accountIdDestination,amount);


    }


    @Override
    public List<AccountDto> AccountList() {

        List<Account> accounts = accountRepo.findAll();
        List<AccountDto> accountsDto=accounts.stream().map(act
                -> mapper.fromAccount(act)).collect(Collectors.toList());
        return accountsDto;


    }

    @Override
    public List<AccountOperationDto> accountHistory(String accountId)
    {
        List<AccountOperation> accountOperation =  accountOpRepo.findByAccountId(accountId);
        return  accountOperation.stream().map(op -> mapper.fromAccountOperation(op)).collect(Collectors.toList());

    }


    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws AccountNotFoundException {
        Account account=accountRepo.findById(accountId).orElse(null);
        if(account==null) throw new AccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOpRepo.findByAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDTO=new AccountHistoryDto();
        List<AccountOperationDto> accountOperationDTOS = accountOperations.getContent().stream().map(op -> mapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(account.getId());
        accountHistoryDTO.setBalance(account.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }





    @Override
    public User getAgent(Long customerId)  {
        User user=userRepo.findById(customerId)

                .orElseThrow(() -> new RuntimeException("Customer Not found"));
        return user;
    }






}