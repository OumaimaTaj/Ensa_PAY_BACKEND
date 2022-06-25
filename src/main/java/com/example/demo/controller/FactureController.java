package com.example.demo.controller;

import com.example.demo.model.Facture;
import com.example.demo.repository.FactureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/facture")
public class FactureController {
    private final FactureRepository factureRepository;

    public FactureController(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }
    @GetMapping("all/{id}")
    public List<Facture> getALlFacture(@PathVariable("id") Long id){
        return factureRepository.findAll();
    }
}
