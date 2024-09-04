package com.eazybytes.springboot.accounts.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountsDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
