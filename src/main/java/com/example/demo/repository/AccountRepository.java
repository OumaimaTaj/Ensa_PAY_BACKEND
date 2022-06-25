package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
@Repository
public interface AccountRepository extends JpaRepository<Account, String > {

    Account findByClient(Client client);
    Account findAccountByRib(long rib);

}
