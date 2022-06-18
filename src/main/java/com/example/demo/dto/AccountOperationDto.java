package com.example.demo.dto;

import com.example.demo.enums.OperationType;
import com.example.demo.model.Account;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class AccountOperationDto {

    private Long id;
    private Date operationDate;
    private Double amount;
    private OperationType type;




}
