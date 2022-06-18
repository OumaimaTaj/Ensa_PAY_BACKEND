package com.example.demo.dto;

import com.example.demo.enums.AccountStatus;
import com.example.demo.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDto {


    private String id;
    private long Rib;
    private int code;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private ClientDTO clientDto;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String number;

    public static Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AccountDto.class.getName()
                    )
            );
        }

        Account account = Account.builder()
                .balance(accountDto.balance)
                .status(accountDto.status)
                .build();
        account.setId(accountDto.getId());

        return account;
    }

    public static AccountDto fromEntity(Account account) {
        if (account == null) {
            throw new RuntimeException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Account.class.getName()
                    )
            );
        }

        return AccountDto.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .status(account.getStatus())
                .Rib(account.getRib())
                .createdAt(account.getCreatedAt())
                .code(account.getCode())
                .build();
    }
}



