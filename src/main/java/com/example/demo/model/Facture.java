package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facture {
    @Id
    @GeneratedValue
    private Long id;
    private String ref;
    private String agence_name;
    private double prix;
    private boolean payed;
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JsonIgnore
    private Client client;
}
