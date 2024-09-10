package com.eazybytes.springboot.accounts.service;

import com.eazybytes.springboot.accounts.dto.CustomerDetailsDto;

public interface CustomerService {
    CustomerDetailsDto fetchCustomerDetails(String phoneNumber);
}
