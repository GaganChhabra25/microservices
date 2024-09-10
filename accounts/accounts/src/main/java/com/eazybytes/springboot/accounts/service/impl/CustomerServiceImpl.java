package com.eazybytes.springboot.accounts.service.impl;

import com.eazybytes.springboot.accounts.dto.CustomerDetailsDto;
import com.eazybytes.springboot.accounts.dto.CustomerDto;
import com.eazybytes.springboot.accounts.dto.client.CardsDto;
import com.eazybytes.springboot.accounts.dto.client.LoansDto;
import com.eazybytes.springboot.accounts.repository.AccountRepository;
import com.eazybytes.springboot.accounts.service.CustomerService;
import com.eazybytes.springboot.accounts.service.client.CardsFeignClient;
import com.eazybytes.springboot.accounts.service.client.LoansFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private LoansFeignClient loansFeignClient;
    @Autowired
    private CardsFeignClient cardsFeignClient;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String phoneNumber) {
        CustomerDto customerDto = accountService.getByPhoneNumber(phoneNumber);
        LoansDto loansDto = loansFeignClient.fetchLoanDetails(phoneNumber);
        CardsDto cardsDto = cardsFeignClient.fetchCardDetails(phoneNumber);

        return CustomerDetailsDto.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(phoneNumber)
                .cardsDto(cardsDto)
                .loansDto(loansDto)
                .build();

    }
}
