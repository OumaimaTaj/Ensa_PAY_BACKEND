package com.example.demo.model;

import com.example.demo.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperation {
@Id
@GeneratedValue
    private Long id;
    private Date operationDate;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private OperationType type;


    @ManyToOne
    private Account account;

}
