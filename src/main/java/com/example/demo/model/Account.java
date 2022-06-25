package com.example.demo.model;


import com.example.demo.enums.AccountStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public  class Account {


    @Id

    private String id;
    private int code;
    private long rib;

    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    private Client client;


    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY, cascade={CascadeType.REMOVE})
    private List<AccountOperation> accountOperations;


}
