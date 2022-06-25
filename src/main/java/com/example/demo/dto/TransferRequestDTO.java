package com.example.demo.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long accountSource;
    private Long accountDestination;
    private double amount;

}

