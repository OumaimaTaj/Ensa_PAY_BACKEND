package com.example.demo.service.ServiceImp;


import com.example.demo.model.Agency;
import com.example.demo.repository.agencesRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgencesService {
    private final agencesRepo agencesRepo;


    public AgencesService(agencesRepo agencesRepo) {
        this.agencesRepo = agencesRepo;
    }

    public Optional<Agency> findAgenceById(int id) {
        return agencesRepo.findById(id);
    }




    public List<Agency> findAllAgences(){
        return agencesRepo.findAll();
    }

}
