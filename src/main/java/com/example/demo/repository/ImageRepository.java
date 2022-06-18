package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
}
