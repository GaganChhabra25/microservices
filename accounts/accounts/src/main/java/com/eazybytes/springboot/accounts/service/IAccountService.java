package com.eazybytes.springboot.accounts.service;

import com.eazybytes.springboot.accounts.dto.CustomerDto;

public interface IAccountService {
    void create(CustomerDto customerDto);
    CustomerDto getByPhoneNumber(String phoneNumber);
    boolean update(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
