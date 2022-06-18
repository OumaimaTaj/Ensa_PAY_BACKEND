package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.io.Serializable;
@Entity
@Table(name = "agencies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String address;
    private String image;
    private String service;
}

