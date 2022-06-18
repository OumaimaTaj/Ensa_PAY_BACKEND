package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonPropertyOrder({ "client" })
public class Client  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @JsonIgnore
    @OneToMany(mappedBy = "client", orphanRemoval = true,cascade=CascadeType.ALL)
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY )
    private List< Account> accounts;


}
