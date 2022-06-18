package com.example.demo.repository;

import com.example.demo.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface agencesRepo extends JpaRepository<Agency, Integer> {
   Agency findById(Long id);



}
