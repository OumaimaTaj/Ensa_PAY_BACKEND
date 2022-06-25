package com.example.demo.controller;

import com.example.demo.model.Agency;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.agencesRepo;
import com.example.demo.service.ServiceImp.AgencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class AgenceController {
    public final AgencesService agencesService;

    @Autowired

    private agencesRepo AgenceRepo;
    @Autowired

    private AccountRepository CompteRepo;

    public AgenceController(AgencesService agencesService) {
        this.agencesService = agencesService;
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/find/{id}")
    public Optional<Agency> getAgenceById (@PathVariable int id) {
        return agencesService.findAgenceById(id);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/all")
    public ResponseEntity<List<Agency>> getAllAgences() {

        List<Agency> agences = agencesService.findAllAgences();

        return new ResponseEntity<>(agences,HttpStatus.OK);
    }


}
