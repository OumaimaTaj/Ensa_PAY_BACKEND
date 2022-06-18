package com.example.demo.controller;

import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.agencesRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class CompteController {


    @Autowired
    private agencesRepo agencesRepo;
    @Autowired
    private AccountRepository paymentRepo;
}
