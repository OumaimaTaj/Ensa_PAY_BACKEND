package com.example.demo.repository;

import com.example.demo.model.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture,Long> {
    List<Facture> findAllByClient(Long id);
}
