package com.eazybytes.springboot.accounts.controller;

import com.eazybytes.springboot.accounts.dto.CustomerDetailsDto;
import com.eazybytes.springboot.accounts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/fetchCustomerDetails")
    private ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam String phoneNumber) {
        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(phoneNumber);
        return ResponseEntity.ok(customerDetailsDto);

    }
}
