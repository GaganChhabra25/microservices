package com.eazybytes.springboot.accounts.dto;

import com.eazybytes.springboot.accounts.dto.client.CardsDto;
import com.eazybytes.springboot.accounts.dto.client.LoansDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDetailsDto {
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min=5, max = 30, message = "Length between 5 and 30")
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    private AccountsDto accountsDto;
    private CardsDto cardsDto;
    private LoansDto loansDto;
}
